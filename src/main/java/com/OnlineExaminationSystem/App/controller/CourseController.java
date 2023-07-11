package com.OnlineExaminationSystem.App.controller;

import com.OnlineExaminationSystem.App.entity.dto.CourseDtoRequest;
import com.OnlineExaminationSystem.App.entity.dto.CourseDtoResponse;
import com.OnlineExaminationSystem.App.entity.dto.userCourseDto.StudentCourseDto;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/add") // Add Course
    public ResponseEntity<CourseDtoResponse> saveCourse(@RequestBody CourseDtoRequest course){
        try {
            CourseDtoResponse savedCourse = this.courseService.saveCourse(course);
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{courseId}") //Delete Course
    public ResponseEntity<Void> deleteCourse(@PathVariable("courseId") long courseId){
        try {
            this.courseService.deleteCourse(courseId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll") // Show Courses List
    public ResponseEntity<List<CourseDtoResponse>> getAllCourses(){
        try {
            List<CourseDtoResponse> courses =  this.courseService.getAll();
            return new ResponseEntity<>(courses, HttpStatus.OK);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{courseId}") // Show Course
    public ResponseEntity<CourseDtoResponse> getCoursesById(@PathVariable("courseId") long courseId) {
        try {
            CourseDtoResponse course = courseService.getCoursesById(courseId);
            return new ResponseEntity<>(course, HttpStatus.OK);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/groupCourses/{groupId}") // Show Course Of Group
    public ResponseEntity<List<CourseDtoResponse>> getCoursesByGroupId(@PathVariable("groupId") long groupId) {
        try {
            List<CourseDtoResponse> courses = courseService.getCoursesByGroupId(groupId);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCoursesByAdminId/{adminId}")
    public ResponseEntity<List<CourseDtoResponse>> getCoursesByAdminId(@PathVariable("adminId") long adminId) {
        try {
            List<CourseDtoResponse> courses = courseService.getCoursesByAdminId(adminId);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (ApiException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getStudentsByCourseId/{courseId}")
    public ResponseEntity<List<StudentCourseDto>> getStudentsByCourseId(@PathVariable("courseId") long courseId) {
        try {
            List<StudentCourseDto> studentDtos = courseService.getStudentsByCourseId(courseId);
            return new ResponseEntity<>(studentDtos, HttpStatus.OK);
        } catch (ApiException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}


