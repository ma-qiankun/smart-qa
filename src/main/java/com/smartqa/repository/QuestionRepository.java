package com.smartqa.repository;

import com.smartqa.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q JOIN FETCH q.askedBy")
    List<Question> findAllWithAskedBy();

    @Query("SELECT q FROM Question q JOIN FETCH q.askedBy WHERE q.id = :id")
    Optional<Question> findByIdWithAskedBy(Long id);
}
