package com.smartqa.service;

import com.smartqa.dto.CreateQuestionRequest;
import com.smartqa.entity.Question;
import com.smartqa.entity.User;
import com.smartqa.repository.QuestionRepository;
import com.smartqa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public QuestionService(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Question create(CreateQuestionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + request.getUserId()));
        Question question = new Question();
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        question.setAskedBy(user);
        return questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public List<Question> findAll() {
        return questionRepository.findAllWithAskedBy();
    }

    @Transactional(readOnly = true)
    public Question findById(Long id) {
        return questionRepository.findByIdWithAskedBy(id)
                .orElseThrow(() -> new IllegalArgumentException("问题不存在: " + id));
    }
}
