package com.smartqa.service;

import com.smartqa.dto.CreateAnswerRequest;
import com.smartqa.entity.Answer;
import com.smartqa.entity.Question;
import com.smartqa.entity.User;
import com.smartqa.repository.AnswerRepository;
import com.smartqa.repository.QuestionRepository;
import com.smartqa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public AnswerService(AnswerRepository answerRepository,
                         QuestionRepository questionRepository,
                         UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Answer create(Long questionId, CreateAnswerRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("问题不存在: " + questionId));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + request.getUserId()));
        Answer answer = new Answer();
        answer.setContent(request.getContent());
        answer.setQuestion(question);
        answer.setAnsweredBy(user);
        return answerRepository.save(answer);
    }

    @Transactional(readOnly = true)
    public List<Answer> findByQuestionId(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new IllegalArgumentException("问题不存在: " + questionId);
        }
        return answerRepository.findByQuestionIdWithAnsweredBy(questionId);
    }
}
