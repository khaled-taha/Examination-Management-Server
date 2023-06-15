package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.Exam.Course;
import com.OnlineExaminationSystem.App.entity.dto.RequestCourseDto;
import com.OnlineExaminationSystem.App.entity.dto.ResponseCourseDto;
import com.OnlineExaminationSystem.App.entity.users.Admin;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.repository.AdminRepository;
import com.OnlineExaminationSystem.App.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AdminRepository adminRepository;

    public ResponseCourseDto saveCourse(RequestCourseDto course){
        List<Admin> admins = this.adminRepository.findAllById(course.getAdminIds());
        Optional<Course> foundCourse = this.courseRepository.findById(course.getId());

        if(!foundCourse.isPresent()) {
            foundCourse = Optional.of(new Course());
        }

        foundCourse.get().setId(course.getId());
        foundCourse.get().setName(course.getName());
        foundCourse.get().setCode(course.getCode());
        foundCourse.get().setGroup(course.getGroup());
        foundCourse.get().setAdmins(admins);

        Course savedCourse = this.courseRepository.save(foundCourse.get());

        return ResponseCourseDto.getCourseDto(savedCourse);
    }

    public void deleteCourse(long courseId){
        this.courseRepository.deleteById(courseId);
    }

    public ResponseCourseDto assignToAdmins(List<Long> adminIds, Long courseId){
        List<Admin> admins = this.adminRepository.findAllById(adminIds);
        Course course = this.courseRepository.findById(courseId).get();
        course.setAdmins(admins);

        this.courseRepository.save(course);

        return ResponseCourseDto.getCourseDto(course);
    }

    public List<ResponseCourseDto> getAll(){
        List<ResponseCourseDto> courseDtos = new ArrayList<>();
        List<Course> courses = this.courseRepository.findAll();
        courses.stream().forEach((course -> {courseDtos.add(ResponseCourseDto.getCourseDto(course));}));
        return courseDtos;
    }
    public List<ResponseCourseDto> getCoursesByGroupId(Long groupId) {
        List<ResponseCourseDto> courseDtos = new ArrayList<>();
        List<Course> courses = courseRepository.findAllByGroupId(groupId);
        courses.stream().forEach((course -> {courseDtos.add(ResponseCourseDto.getCourseDto(course));}));
        return courseDtos;
    }

    public ResponseCourseDto getCoursesById(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if(!course.isPresent()) throw new ApiException("Course not found");
        return ResponseCourseDto.getCourseDto(course.get());
    }
}
