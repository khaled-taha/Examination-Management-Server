package com.OnlineExaminationSystem.App.controller;

import com.OnlineExaminationSystem.App.entity.dto.user.student.StudentDto;
import com.OnlineExaminationSystem.App.entity.users.Student;
import com.OnlineExaminationSystem.App.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;


    @Operation(summary = "To get all students from DB")
    @GetMapping(path = "/getAll") // Show Students List
    public ResponseEntity<List<StudentDto>> getStudents() {
        return new ResponseEntity<>(this.studentService.getAllStudents(), HttpStatus.OK);
    }

    @Operation(summary = "To get a student from DB by id")
    @GetMapping(path = "/get/{id}") // Show Student
    public ResponseEntity<StudentDto> getStudent(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.studentService.getStudentById(id), HttpStatus.OK);
    }

    @Operation(summary = "To add a student to DB. You will add without id key of JSON or set Id = 0.  " +
            "Set the password with value (firstName + LastName + university id) by default." +
            "At the same time, you can set it manually.")
    @PostMapping(path = "/add") // Add Student
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto student) {
        StudentDto st = this.studentService.saveStudent(student);
        return new ResponseEntity<>(st, HttpStatus.OK);
    }

    @Operation(summary = "To update a student in DB.")
    @PostMapping(path = "/update") // Update Student
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto student) {
        StudentDto st = this.studentService.saveStudent(student);
        return new ResponseEntity<>(st, HttpStatus.OK);
    }

    @Operation(summary = "To delete a student from DB by id")
    @DeleteMapping(path = "/delete/{id}") // Delete Student
    public ResponseEntity<?> deleteStudent(@PathVariable("id") long studentId) {
        this.studentService.deleteById(studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
