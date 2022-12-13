package com.OnlineExaminationSystem.App.controller;


import com.OnlineExaminationSystem.App.entity.users.Admin;
import com.OnlineExaminationSystem.App.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/admins")

public class AdminController {

    private final AdminService adminService;


    @Operation(summary = "To get all admins from DB")
    @CrossOrigin(origins = "*", originPatterns = ".*")
    @GetMapping(path = "/getAll")
    public ResponseEntity<List<Admin>> getStudents() {
        return new ResponseEntity<>(this.adminService.getAllAdmins(), HttpStatus.OK);
    }

    @Operation(summary = "To get an admin from DB by id")
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Admin> getStudent(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.adminService.getAdminById(id), HttpStatus.OK);
    }


    @Operation(summary = "To add an admin to DB. You will add without id key of JSON or set Id = 0.  " +
            "Set the password with value (firstName + LastName + university id) by default." +
            "At the same time, you can set it manually.")
    @CrossOrigin(origins = "*")
    @PostMapping(path = "/add")
    public ResponseEntity<Admin> addStudent(@RequestBody Admin admin) {
        Admin adm =  this.adminService.addAndUpdateAdmin(admin);
        return new ResponseEntity<>(adm, HttpStatus.OK);
    }

    @Operation(summary = "To update an admin in DB.")
    @CrossOrigin(origins = "*")
    @PostMapping(path = "/update")
    public ResponseEntity<Admin> updateStudent(@RequestBody Admin admin) {
        Admin adm =  this.adminService.addAndUpdateAdmin(admin);
        return new ResponseEntity<>(adm, HttpStatus.OK);
    }

    @Operation(summary = "To delete an admin from DB by id")
    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
        this.adminService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
