package org.example.chatservice.mapper;

import org.example.chatservice.dto.reaction.ReactionRequestDto;
import org.example.chatservice.dto.reaction.ReactionResponseDto;
import org.example.chatservice.entities.Reaction;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ReactionMapper {

    @Mapping(target = "timestamp", expression = "java(System.currentTimeMillis())")
    Reaction reactionRequestDtoToReaction(ReactionRequestDto reactionRequestDto);

    ReactionResponseDto reactionToReactionResponseDto(Reaction reaction);

    List<ReactionResponseDto> reactionsToReactionResponseDtoList(List<Reaction> reactions);

}
