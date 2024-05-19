package com.example.userService.services;

import com.example.userService.models.Role;
import com.example.userService.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }
    public Role createRole(String name) {
        Role role=new Role();
        role.setRole(name);
        return roleRepository.save(role);
    }
    /*public Role createRole(String name){

    }*/
}
