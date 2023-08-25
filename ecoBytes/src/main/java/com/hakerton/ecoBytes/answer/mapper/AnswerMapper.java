package com.hakerton.ecoBytes.answer.mapper;

import com.hakerton.ecoBytes.answer.db.Answer;
import com.hakerton.ecoBytes.answer.model.AnswerPostDto;
import com.hakerton.ecoBytes.answer.model.AnswerResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    Answer answerpostToAnswer(AnswerPostDto answerPostDto);
    AnswerResponseDto answerToAnswerResponseDto(Answer answer);
    List<AnswerResponseDto> answerListToAnswerResponList(List<Answer> answerList);
}
