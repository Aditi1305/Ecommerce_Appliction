package com.example.userService.controllers;

import com.example.userService.dto.CreateRoleRequestDto;
import com.example.userService.models.Role;
import com.example.userService.repositories.RoleRepository;
import com.example.userService.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService){
        this.roleService=roleService;
    }
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto request){
        Role role=roleService.createRole(request.getName());
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

}
