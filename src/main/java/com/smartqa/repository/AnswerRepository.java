package com.smartqa.repository;

import com.smartqa.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("SELECT a FROM Answer a JOIN FETCH a.answeredBy WHERE a.question.id = :questionId")
    List<Answer> findByQuestionIdWithAnsweredBy(Long questionId);
}
