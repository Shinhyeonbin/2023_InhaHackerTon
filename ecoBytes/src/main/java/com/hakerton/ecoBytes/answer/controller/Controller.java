package com.hakerton.ecoBytes.answer.controller;

import com.hakerton.ecoBytes.answer.db.Answer;
import com.hakerton.ecoBytes.answer.mapper.AnswerMapper;
import com.hakerton.ecoBytes.answer.model.AnswerPostDto;
import com.hakerton.ecoBytes.answer.model.AnswerResponseDto;
import com.hakerton.ecoBytes.answer.service.AnswerService;
import com.hakerton.ecoBytes.pagenation.API;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final AnswerService answerService;
    private final AnswerMapper answerMapper;

    @PostMapping("/post")
    public ResponseEntity post(@RequestBody AnswerPostDto answerPostDto){
        Answer answer = answerService.post(answerMapper.answerpostToAnswer(answerPostDto));
        return  new ResponseEntity(answerMapper.answerToAnswerResponseDto(answer), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity post(@PageableDefault(page = 0, size = 5) Pageable pageable){
        API<List<AnswerResponseDto>> answerList  = answerService.getPage(pageable);
        return  new ResponseEntity(answerList, HttpStatus.CREATED);
    }
}
