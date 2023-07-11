package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.Exam.Course;
import com.OnlineExaminationSystem.App.entity.dto.CourseDtoRequest;
import com.OnlineExaminationSystem.App.entity.dto.CourseDtoResponse;
import com.OnlineExaminationSystem.App.entity.dto.userCourseDto.StudentCourseDto;
import com.OnlineExaminationSystem.App.entity.users.Admin;
import com.OnlineExaminationSystem.App.entity.users.Student;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.repository.AdminRepository;
import com.OnlineExaminationSystem.App.repository.CourseRepository;
import com.OnlineExaminationSystem.App.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;


    @Autowired
    private AdminService adminService;

    public CourseDtoResponse saveCourse(CourseDtoRequest course){
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

        return CourseDtoResponse.getCourseDto(savedCourse);
    }

    public void deleteCourse(long courseId){
        this.courseRepository.deleteById(courseId);
    }


    public List<CourseDtoResponse> getAll(){
        List<CourseDtoResponse> courseDtos = new ArrayList<>();
        List<Course> courses = this.courseRepository.findAll();
        courses.stream().forEach((course -> {courseDtos.add(CourseDtoResponse.getCourseDto(course));}));
        return courseDtos;
    }

    public List<CourseDtoResponse> getCoursesByGroupId(Long groupId) {
        List<CourseDtoResponse> courseDtos = new ArrayList<>();
        List<Course> courses = courseRepository.findAllByGroupId(groupId);
        courses.stream().forEach((course -> {courseDtos.add(CourseDtoResponse.getCourseDto(course));}));
        return courseDtos;
    }

    public CourseDtoResponse getCoursesById(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if(!course.isPresent()) throw new ApiException("Course not found");
        return CourseDtoResponse.getCourseDto(course.get());
    }


    public List<CourseDtoResponse> getCoursesByAdminId(long adminId) {

        Optional<Admin> admin = adminRepository.findAdminById(adminId);
        if (admin.isEmpty()) {
            throw new ApiException("Admin not found");
        }

        List<Course> courses = courseRepository.findCoursesByAdminsContaining(admin.get());
        return courses.stream()
                .map(CourseDtoResponse::getCourseDto)
                .collect(Collectors.toList());
    }


    public List<StudentCourseDto> getStudentsByCourseId(long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ApiException("Course not found"));

        List<Student> students = studentRepository.findStudentsByGroup(course.getGroup());
        return students.stream()
                .map(StudentCourseDto::getStudentDto)
                .collect(Collectors.toList());
    }
}
