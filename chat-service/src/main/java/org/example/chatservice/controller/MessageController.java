package org.example.chatservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.example.chatservice.dto.message.MessageRequestDto;
import org.example.chatservice.dto.message.MessageResponseDto;
import org.example.chatservice.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<MessageResponseDto> createMessage(@RequestBody @Valid MessageRequestDto requestDto) {
        MessageResponseDto responseDto = messageService.sendMessage(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @DeleteMapping("/{messageId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<MessageResponseDto> deleteMessage(@PathVariable String messageId) {
        ObjectId objectId = new ObjectId(messageId);
        MessageResponseDto responseDto = messageService.deleteMessage(objectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseDto);
    }

    @GetMapping("/{chatId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<MessageResponseDto>> getMessages(@PathVariable @Valid UUID chatId) {
        List<MessageResponseDto> messages = messageService.getAllMessages(chatId);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
}
