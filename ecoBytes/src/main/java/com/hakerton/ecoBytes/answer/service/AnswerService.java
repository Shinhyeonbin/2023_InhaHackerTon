package com.hakerton.ecoBytes.answer.service;

import com.hakerton.ecoBytes.answer.db.Answer;
import com.hakerton.ecoBytes.answer.db.AnswerRepository;
import com.hakerton.ecoBytes.answer.mapper.AnswerMapper;
import com.hakerton.ecoBytes.answer.model.AnswerResponseDto;
import com.hakerton.ecoBytes.pagenation.API;
import com.hakerton.ecoBytes.pagenation.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    public Answer post(Answer answerpostToAnswer) {
        return answerRepository.save(answerpostToAnswer);
    }

    public API<List<AnswerResponseDto>> getPage(Pageable pageable){
            var list =  answerRepository.findAll(pageable);
            var pagination = Pagination.builder().page(list.getNumber())
                    .size(list.getSize())
                    .currentElements(list.getNumberOfElements())
                    .totalElements(list.getTotalElements())
                    .totalPage(list.getTotalPages())
                    .build();
            List<Answer> answerList = list.getContent();
            List<AnswerResponseDto> answerResponseDtos = answerMapper.answerListToAnswerResponList(answerList);
            var api = API.<List<AnswerResponseDto>>builder().body(answerResponseDtos).pagination(pagination).build();
            return api;
        }
    }

