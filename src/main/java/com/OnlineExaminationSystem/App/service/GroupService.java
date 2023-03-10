package com.OnlineExaminationSystem.App.service;


import com.OnlineExaminationSystem.App.entity.Exam.Group;
import com.OnlineExaminationSystem.App.repository.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group saveGroup(Group group){
       return this.groupRepository.save(group);
    }

    public void deleteGroup(long groupId){
        this.groupRepository.deleteById(groupId);
    }

    public List<Group> getAll(){
        return this.groupRepository.findAll();
    }

}
