package com.OnlineExaminationSystem.App.controller;

import com.OnlineExaminationSystem.App.entity.users.Privilege;
import com.OnlineExaminationSystem.App.service.PrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/privilege")
public class PrivilegeController {

    @Autowired
    private final PrivilegeService privilegeService;

    @GetMapping // Show Privileges List
    public ResponseEntity<List<Privilege>> getAll(){
        return new ResponseEntity<>(this.privilegeService.getAll(), HttpStatus.OK);
    }
}
