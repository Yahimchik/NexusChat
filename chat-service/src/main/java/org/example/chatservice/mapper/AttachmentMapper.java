package org.example.chatservice.mapper;

import org.example.chatservice.dto.attachment.AttachmentRequestDto;
import org.example.chatservice.dto.attachment.AttachmentResponseDto;
import org.example.chatservice.entities.Attachment;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface AttachmentMapper {

    @Mapping(target = "timestamp", expression = "java(System.currentTimeMillis())")
    Attachment attachmentRequestDtoToAttachment(AttachmentRequestDto attachmentRequestDto);

    AttachmentResponseDto attachmentToAttachmentResponseDto(Attachment attachment);

    List<AttachmentResponseDto> attachmentsToAttachmentResponseDtoList(List<Attachment> attachments);
}