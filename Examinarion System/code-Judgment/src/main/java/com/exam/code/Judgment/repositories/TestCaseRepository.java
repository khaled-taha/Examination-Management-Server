package com.exam.code.Judgment.repositories;

import com.exam.code.Judgment.models.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
}