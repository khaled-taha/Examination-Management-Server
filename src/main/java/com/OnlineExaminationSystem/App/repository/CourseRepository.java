package com.OnlineExaminationSystem.App.repository;

import com.OnlineExaminationSystem.App.entity.Exam.Course;
import com.OnlineExaminationSystem.App.entity.Exam.Exam;
import com.OnlineExaminationSystem.App.entity.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByGroupId(Long groupId);

    List<Course> findCoursesByAdminsContaining(Admin admin);
}
