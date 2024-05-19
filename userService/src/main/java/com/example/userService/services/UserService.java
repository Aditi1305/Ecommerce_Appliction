package com.example.userService.services;

import com.example.userService.dto.UserDto;
import com.example.userService.models.Role;
import com.example.userService.models.User;
import com.example.userService.repositories.RoleRepository;
import com.example.userService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    @Autowired
    UserService(UserRepository userRepository,RoleRepository roleRepository){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
    }

    public List<User> getUserDetails() {
        List<User> userOptional=userRepository.findAll();
        return userOptional;

    }

    public UserDto setUserDetails(Long userId, List<Long> roleId) {
        Optional<User> userOptional=userRepository.findById(userId);
        List<Role> roleOptional=roleRepository.findAllByIdIn(roleId);
        if(userOptional.isEmpty()){
            return null;
        }
        Set<Role> savedRole=new HashSet(roleOptional);
        User user= userOptional.get();
        user.setRoles(savedRole);
        System.out.println("Hi"+user.getRoles());
        User savedUser=userRepository.save(user);

        return UserDto.from(savedUser);
    }

}
