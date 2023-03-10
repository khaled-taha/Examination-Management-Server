package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.Exam.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {


    @Query("SELECT e.course.group FROM Exam e WHERE e.id = :examId")
    List<Group> findGroupsByExamId(@Param("examId") Long examId);
}
