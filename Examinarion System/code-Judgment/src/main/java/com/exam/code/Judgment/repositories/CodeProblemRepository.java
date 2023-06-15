package com.exam.code.Judgment.repositories;

import com.exam.code.Judgment.models.CodeProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeProblemRepository extends JpaRepository<CodeProblem, Long> {
    Optional<CodeProblem> findByExamId(long examId);
    Optional<List<CodeProblem>> findAllByExamId(long examId);
}