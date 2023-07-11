package com.OnlineExaminationSystem.App.service;


import com.OnlineExaminationSystem.App.entity.users.Privilege;
import com.OnlineExaminationSystem.App.repository.PrivilegeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PrivilegeService {

    @Autowired
    private final PrivilegeRepository privilegeRepository;

    public List<Privilege> getAll(){
        return this.privilegeRepository.findAll();
    }
}
