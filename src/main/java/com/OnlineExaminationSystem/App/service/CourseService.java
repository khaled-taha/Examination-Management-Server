package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.Exam.Course;
import com.OnlineExaminationSystem.App.entity.dto.RequestCourseDto;
import com.OnlineExaminationSystem.App.entity.dto.ResponseCourseDto;
import com.OnlineExaminationSystem.App.entity.users.Admin;
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
        Optional<Course> savedCourse = this.courseRepository.findById(course.getId());

        if(!savedCourse.isPresent()) {
            savedCourse = Optional.of(new Course());
        }

        savedCourse.get().setId(course.getId());
        savedCourse.get().setName(course.getName());
        savedCourse.get().setCode(course.getCode());
        savedCourse.get().setGroup(course.getGroup());
        savedCourse.get().setAdmins(admins);

        System.out.println(course.getCode());

        Course responseCourse = this.courseRepository.save(savedCourse.get());

        return ResponseCourseDto.getCourseDto(responseCourse, admins);
    }

    public void deleteCourse(long courseId){
        this.courseRepository.deleteById(courseId);
    }

    public ResponseCourseDto assignToAdmins(List<Long> adminIds, Long courseId){
        List<Admin> admins = this.adminRepository.findAllById(adminIds);
        Course course = this.courseRepository.findById(courseId).get();
        course.setAdmins(admins);

        this.courseRepository.save(course);

        return ResponseCourseDto.getCourseDto(course, admins);
    }

    public List<ResponseCourseDto> getAll(){
        List<ResponseCourseDto> courseDtos = new ArrayList<>();
        List<Course> courses = this.courseRepository.findAll();
        courses.stream().forEach((course -> {courseDtos.add(ResponseCourseDto.getCourseDto(course, course.getAdmins()));}));
        return courseDtos;
    }
    public List<ResponseCourseDto> getCoursesByGroupId(Long groupId) {
        List<ResponseCourseDto> courseDtos = new ArrayList<>();
        List<Course> courses = courseRepository.findAllByGroupId(groupId);
        courses.stream().forEach((course -> {courseDtos.add(ResponseCourseDto.getCourseDto(course, course.getAdmins()));}));
        return courseDtos;
    }
}
