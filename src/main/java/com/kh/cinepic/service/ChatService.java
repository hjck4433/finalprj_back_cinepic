package com.kh.cinepic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.cinepic.dto.ChatMsgDto;
import com.kh.cinepic.dto.ChatRoomResDto;
import com.kh.cinepic.entity.ChatRoom;
import com.kh.cinepic.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private Map<String, ChatRoomResDto> chatRooms;

    @PostConstruct // 의존성 주입 이후 초기화 수행
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }
    public List<ChatRoomResDto> findAllRoom() {
        List<ChatRoomResDto> roomList = new ArrayList<>(chatRooms.values());
        if(roomList != null) {
            return roomList;
        }else {
            return null;
        }
    }
    public ChatRoomResDto findRoomById(String roomId){
        return chatRooms.get(roomId);
    }

    // 방 개설
    public ChatRoomResDto createRoom(String roomName) {
        String randomId = UUID.randomUUID().toString();
        log.info("UUID : {}", randomId);
        ChatRoomResDto chatRoom = ChatRoomResDto.builder()
                .roomId(randomId)
                .roomName(roomName)
                .regDate(LocalDateTime.now())
                .build();
        chatRooms.put(randomId, chatRoom);

        // DB에 새로운 채팅방 저장
        ChatRoom newChatRoom = new ChatRoom();
        newChatRoom.setRoomId(chatRoom.getRoomId());
        newChatRoom.setRoomName(chatRoom.getRoomName());
        newChatRoom.setCreatedAt(chatRoom.getRegDate());
        chatRoomRepository.save(newChatRoom);

        return chatRoom;
    }

    // 채팅방 삭제
    public void removeRoom(String roomId) {
        ChatRoomResDto room = chatRooms.get(roomId);
        if (room != null) {
            if(room.isSessionEmpty()) {
                chatRooms.remove(roomId);
                //DB에서도 채팅방 삭제
                chatRoomRepository.deleteById(roomId);
            }
        }
    }

    // 채팅방 입장
    public void addSessionAndHandleEnter(String roomId, WebSocketSession session, ChatMsgDto chatMsg) {
        ChatRoomResDto room = findRoomById(roomId);
        if (room != null) {
            room.getSessions().add(session);
            if(chatMsg.getSender() != null) {
                chatMsg.setMsg(chatMsg.getSenderAlias() + "님이 입장했습니다.");
                sendMsgToAll(roomId, chatMsg);
            }
            log.debug("New session added: {}", session);
        }
    }

    // 채팅방 퇴장
    public void removeSessionAndHandleExit(String roomId, WebSocketSession session, ChatMsgDto chatMsg){
        ChatRoomResDto room = findRoomById(roomId);
        if (room != null) {
            room.getSessions().remove(session);
            if(chatMsg.getSender() != null) {
                chatMsg.setMsg(chatMsg.getSenderAlias() + "님이 퇴장했습니다.");
                sendMsgToAll(roomId, chatMsg);
            }
            log.debug("Session removed : {}", session);
            if(room.isSessionEmpty()) {
                removeRoom(roomId);
            }
        }
    }

    // 메세지 보내기
    public void sendMsgToAll(String roomId, ChatMsgDto msg){
        ChatRoomResDto room = findRoomById(roomId);
        if(room != null) {
            for (WebSocketSession session : room.getSessions()) {
                sendMsg(session, msg);
            }
        }
    }

    public <T> void sendMsg(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch(IOException e) {
            log.error(e.getLocalizedMessage(),e);
        }
    }


}
