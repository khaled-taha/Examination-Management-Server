package com.OnlineExaminationSystem.App.controller;
import com.OnlineExaminationSystem.App.entity.users.Student;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;


    @Operation(summary = "To get all students from DB")
    @GetMapping(path = "/getAll")
    public ResponseEntity<List<Student>> getStudents() {
        return new ResponseEntity<>(this.studentService.getAllStudents(), HttpStatus.OK);
    }

    @Operation(summary = "To get a student from DB by id")
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.studentService.getStudentById(id), HttpStatus.OK);
    }

    @Operation(summary = "To add a student to DB. You will add without id key of JSON or set Id = 0.  " +
            "Set the password with value (firstName + LastName + university id) by default." +
            "At the same time, you can set it manually.")
    @PostMapping(path = "/add")
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            FieldError fe = bindingResult.getFieldError();
            throw new ApiException(fe.getField() + " : " + fe.getDefaultMessage());
        }
        Student st = this.studentService.addAndUpdateStudent(student);
        return new ResponseEntity<>(st, HttpStatus.OK);
    }

    @Operation(summary = "To update a student in DB.")
    @PostMapping(path = "/update")
    public ResponseEntity<Student> updateStudent(@Valid @RequestBody Student student, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            FieldError fe = bindingResult.getFieldError();
            throw new ApiException(fe.getField() + " : " + fe.getDefaultMessage());
        }
        Student st = this.studentService.addAndUpdateStudent(student);
        return new ResponseEntity<>(st, HttpStatus.OK);
    }

    @Operation(summary = "To delete a student from DB by id")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long studentId) {
        this.studentService.deleteById(studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
