package com.OnlineExaminationSystem.App.controller;


import com.OnlineExaminationSystem.App.entity.users.Role;
import com.OnlineExaminationSystem.App.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/roles")
public class RoleController {

    private final RoleService roleService;


    @Operation(summary = "To get all roles from DB.")
    @GetMapping(path = "/getAll") // Show Roles List
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(this.roleService.getAllRoles(), HttpStatus.OK);
    }

    @Operation(summary = "To get a role from DB by id")
    @GetMapping(path = "/get/{id}") // Show Role
    public ResponseEntity<Role> getRole(@PathVariable("id") int id) {
        return new ResponseEntity<>(this.roleService.findRoleById(id), HttpStatus.OK);
    }


    @Operation(summary = "To add a role to DB. You will add without id key of JSON or set Id = 0")
    @PostMapping(path = "/add") // Add Role
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        Role ro =  this.roleService.addAndUpdateRole(role);
        return new ResponseEntity<>(ro, HttpStatus.OK);
    }

    @Operation(summary = "To update a role in DB.")
    @PostMapping(path = "/update") // Update Role
    public ResponseEntity<Role> updateRole(@RequestBody Role role) {
        Role ro =  this.roleService.addAndUpdateRole(role);
        return new ResponseEntity<>(ro, HttpStatus.OK);
    }

    @Operation(summary = "To delete a role from DB by id")
    @DeleteMapping(path = "/delete/{id}") // Delete Role
    public ResponseEntity<?> deleteRole(@PathVariable("id") int id) {
        this.roleService.deleteRoleById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
