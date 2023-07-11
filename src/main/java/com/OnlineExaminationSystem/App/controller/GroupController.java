package com.OnlineExaminationSystem.App.controller;


import com.OnlineExaminationSystem.App.entity.Exam.Group;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.service.GroupService;
import com.OnlineExaminationSystem.App.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {

    @Autowired
    private GroupService groupService;

    private StudentService studentService;

    @PostMapping("/save") // Add Group
    public ResponseEntity<Group> saveGroup(@RequestBody Group group){
        System.out.println(group);
        try {
            Group savedGroup = this.groupService.saveGroup(group);

            return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{groupId}") // Delete Group
    public ResponseEntity<Void> deleteGroup(@PathVariable("groupId") long groupId){
        try {
            this.groupService.deleteGroup(groupId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll") // Show Groups List
    public ResponseEntity<List<Group>> getAllGroups(){
        try {
            List<Group> groups =  this.groupService.getAll();
            return new ResponseEntity<>(groups, HttpStatus.OK);
        }catch (ApiException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/StudentGroup/{studentId}") // Show Student
    public ResponseEntity<Long> getStudent(@PathVariable("studentId") Long id) {
        return new ResponseEntity<>(this.studentService.getGroupOfStudent(id), HttpStatus.OK);
    }
}
