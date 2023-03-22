package com.OnlineExaminationSystem.App.controller;

import com.OnlineExaminationSystem.App.entity.Exam.Course;
import com.OnlineExaminationSystem.App.entity.dto.RequestCourseDto;
import com.OnlineExaminationSystem.App.entity.dto.ResponseCourseDto;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<ResponseCourseDto> saveCourse(@RequestBody RequestCourseDto course){
        try {
            ResponseCourseDto savedCourse = this.courseService.saveCourse(course);
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("courseId") long courseId){
        try {
            this.courseService.deleteCourse(courseId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ResponseCourseDto>> getAllCourses(){
        try {
            List<ResponseCourseDto> courses =  this.courseService.getAll();
            return new ResponseEntity<>(courses, HttpStatus.OK);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<ResponseCourseDto> getCoursesById(@PathVariable("courseId") long courseId) {
        try {
            ResponseCourseDto course = courseService.getCoursesById(courseId);
            return new ResponseEntity<>(course, HttpStatus.OK);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/groupCourses/{groupId}")
    public ResponseEntity<List<ResponseCourseDto>> getCoursesByGroupId(@PathVariable("groupId") long groupId) {
        try {
            List<ResponseCourseDto> courses = courseService.getCoursesByGroupId(groupId);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
/*

    @PostMapping("manage/{courseId}")
    public ResponseEntity<CourseDto> assignToAdmins(@RequestBody List<Long> adminIds,
                                                    @PathVariable("courseId") Long courseId){
        try {
            CourseDto savedCourse = this.courseService.assignToAdmins(adminIds, courseId);
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
*/

}
