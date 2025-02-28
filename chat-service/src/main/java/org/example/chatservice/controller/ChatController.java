package org.example.chatservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.chatservice.dto.chat.ChatRequestDto;
import org.example.chatservice.dto.chat.ChatResponseDto;
import org.example.chatservice.services.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatResponseDto> createChat(@RequestBody @Valid ChatRequestDto requestDto) {
        ChatResponseDto responseDto = chatService.createChat(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatResponseDto> getChatById(@PathVariable UUID chatId) {
        ChatResponseDto responseDto = chatService.getChatById(chatId);
        return ResponseEntity.ok(responseDto);
    }


    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable UUID chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{chatId}/users")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> addUserToChat(@PathVariable UUID chatId) {
        chatService.addUserToChat(chatId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{chatId}/users")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> removeUserFromChat(@PathVariable UUID chatId) {
        chatService.removeUserFromChat(chatId);
        return ResponseEntity.ok().build();
    }
}
