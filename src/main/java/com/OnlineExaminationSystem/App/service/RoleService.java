package com.OnlineExaminationSystem.App.service;

import com.OnlineExaminationSystem.App.entity.users.Role;
import com.OnlineExaminationSystem.App.exceptions.ApiException;
import com.OnlineExaminationSystem.App.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public Role addAndUpdateRole(Role role){

        if(role.getId() == 0 && this.roleRepository.findByRole(role.getRole()).isPresent()){
            throw new ApiException("Duplicate Role");
        }

        this.roleRepository.save(role);
        return role;
    }

    public List<Role> getAllRoles(){
        return this.roleRepository.findAll();
    }

    public void deleteRoleById(int id){
        Role role = this.roleRepository.findRoleById(id)
               .orElseThrow(() -> new ApiException("Role not found"));

        this.roleRepository.deleteById(id);
    }

    public Role findRoleById(int id){
        return this.roleRepository.findRoleById(id)
                .orElseThrow(() -> new ApiException("Role not found"));
    }


}
