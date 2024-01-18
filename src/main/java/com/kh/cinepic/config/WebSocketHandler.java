package com.kh.cinepic.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.cinepic.dto.ChatMsgDto;
import com.kh.cinepic.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final Map<WebSocketSession, String> sessionRoomIdMap = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String payload = message.getPayload();
        log.warn("{}", payload);
        // objectMapper가 json데이터를 객체로 바꿔줌
        ChatMsgDto chatMsg = objectMapper.readValue(payload, ChatMsgDto.class);
        String roomId = chatMsg.getRoomId();
        //세션과 채팅방 Id를 매핑
        sessionRoomIdMap.put(session, chatMsg.getRoomId());
        if (chatMsg.getType() == ChatMsgDto.MessageType.ENTER) {
            chatService.addSessionAndHandleEnter(roomId, session, chatMsg);
        }else if (chatMsg.getType() == ChatMsgDto.MessageType.CLOSE) {
            chatService.removeSessionAndHandleExit(roomId, session, chatMsg);
        }else {
            chatService.sendMsgToAll(roomId, chatMsg);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        //세션과 매핑된 채팅방 ID 가져오기
        String roomId = sessionRoomIdMap.remove(session);
        if (roomId != null) {
            ChatMsgDto chatMsg = new ChatMsgDto();
            chatMsg.setType(ChatMsgDto.MessageType.CLOSE);
            chatService.removeSessionAndHandleExit(roomId,session, chatMsg);
        }
    }
}
