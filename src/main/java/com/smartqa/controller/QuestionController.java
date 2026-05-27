package com.smartqa.controller;

import com.smartqa.dto.CreateQuestionRequest;
import com.smartqa.entity.Question;
import com.smartqa.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<Question> create(@Valid @RequestBody CreateQuestionRequest request) {
        return ResponseEntity.ok(questionService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<Question>> findAll() {
        return ResponseEntity.ok(questionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> findById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.findById(id));
    }
}
