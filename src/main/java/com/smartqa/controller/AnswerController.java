package com.smartqa.controller;

import com.smartqa.dto.CreateAnswerRequest;
import com.smartqa.entity.Answer;
import com.smartqa.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<Answer> create(@PathVariable Long questionId,
                                         @Valid @RequestBody CreateAnswerRequest request) {
        return ResponseEntity.ok(answerService.create(questionId, request));
    }

    @GetMapping
    public ResponseEntity<List<Answer>> findByQuestionId(@PathVariable Long questionId) {
        return ResponseEntity.ok(answerService.findByQuestionId(questionId));
    }
}
