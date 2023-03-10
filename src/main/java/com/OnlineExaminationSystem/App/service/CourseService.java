package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.Exam.Course;
import com.OnlineExaminationSystem.App.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course saveCourse(Course course){
        return this.courseRepository.save(course);
    }

    public void deleteCourse(long courseId){
        this.courseRepository.deleteById(courseId);
    }

    public List<Course> getAll(){
        return this.courseRepository.findAll();
    }

    public List<Course> getCoursesByGroupId(Long groupId) {
        return courseRepository.findAllByGroupId(groupId);
    }
}
