package org.example.chatservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.example.chatservice.dto.message.MessageResponseDto;
import org.example.chatservice.services.MessageService;
import org.example.dto.MessageRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

//    @PostMapping("/send")
//    @SecurityRequirement(name = "Bearer Authentication")
//    public ResponseEntity<MessageResponseDto> createMessage(@RequestBody @Valid MessageRequestDto requestDto) {
//        MessageResponseDto responseDto = messageService.saveMessage(requestDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
//    }

    @DeleteMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<MessageResponseDto> deleteMessage(@RequestParam String messageId) {
        ObjectId objectId = new ObjectId(messageId);
        MessageResponseDto responseDto = messageService.deleteMessage(objectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseDto);
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<MessageResponseDto>> getMessages(@RequestParam @Valid UUID chatId) {
        List<MessageResponseDto> messages = messageService.getAllMessages(chatId);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @PatchMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> editMessage(@RequestParam @Valid String messageId, @RequestParam @Valid String newText) {
        ObjectId objectId = new ObjectId(messageId);
        messageService.editMessage(objectId, newText);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/isRead")
    public ResponseEntity<Void> isRead(@RequestParam @Valid String messageId) {
        ObjectId objectId = new ObjectId(messageId);
        messageService.markAsRead(objectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/search")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<MessageResponseDto>> findByText(@RequestParam String text) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.findByText(text));
    }
}
