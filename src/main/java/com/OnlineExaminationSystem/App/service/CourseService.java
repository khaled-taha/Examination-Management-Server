package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.Exam.Course;
import com.OnlineExaminationSystem.App.entity.dto.CourseDto;
import com.OnlineExaminationSystem.App.entity.users.Admin;
import com.OnlineExaminationSystem.App.repository.AdminRepository;
import com.OnlineExaminationSystem.App.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AdminRepository adminRepository;

    public CourseDto saveCourse(Course course, List<Long> adminIds){
        List<Admin> admins = this.adminRepository.findAllById(adminIds);
        course.setAdmins(admins);

        Course savedCourse = this.courseRepository.save(course);

        return CourseDto.getCourseDto(course, admins);
    }

    public void deleteCourse(long courseId){
        this.courseRepository.deleteById(courseId);
    }

    public CourseDto assignToAdmins(List<Long> adminIds, Long courseId){
        List<Admin> admins = this.adminRepository.findAllById(adminIds);
        Course course = this.courseRepository.findById(courseId).get();
        course.setAdmins(admins);

        this.courseRepository.save(course);

        return CourseDto.getCourseDto(course, admins);
    }

    public List<CourseDto> getAll(){
        List<CourseDto> courseDtos = new ArrayList<>();
        List<Course> courses = this.courseRepository.findAll();
        courses.stream().forEach((course -> {courseDtos.add(CourseDto.getCourseDto(course, course.getAdmins()));}));
        return courseDtos;
    }
    public List<CourseDto> getCoursesByGroupId(Long groupId) {
        List<CourseDto> courseDtos = new ArrayList<>();
        List<Course> courses = courseRepository.findAllByGroupId(groupId);
        courses.stream().forEach((course -> {courseDtos.add(CourseDto.getCourseDto(course, course.getAdmins()));}));
        return courseDtos;
    }
}
