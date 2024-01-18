package com.kh.cinepic.controller;

import com.kh.cinepic.dto.ChatRoomResDto;
import com.kh.cinepic.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    // 방 개설
    @PostMapping("/new")
    public ResponseEntity<String> createRoom(@RequestBody Map<String, String> name) {
        String roomName = name.get("roomName");
        log.warn("roomName : {}", roomName);
        ChatRoomResDto room = chatService.createRoom(roomName);
        log.info("roomId : {}", room.getRoomId());
        return ResponseEntity.ok(room.getRoomId());
    }

    // 방 리스트 반환
    @GetMapping("/list")
    public ResponseEntity<List<ChatRoomResDto>> findAllRoom(){
        log.info("chatList : {}", chatService.findAllRoom());
        return ResponseEntity.ok(chatService.findAllRoom());
    }

    // 특정 방 조회
    @GetMapping("/chatroom/{roomId}")
    public ResponseEntity<ChatRoomResDto> chatRoomInfo(@PathVariable String roomId) {
        ChatRoomResDto room = chatService.findRoomById(roomId);
        return ResponseEntity.ok(room);
    }
}
