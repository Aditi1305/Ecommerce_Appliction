package com.example.userService.controllers;

import com.example.userService.dto.SetUserRolesRequestDto;
import com.example.userService.dto.UserDto;
import com.example.userService.models.User;
import com.example.userService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;


    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUserDetails(){
        List<User> userDtos=userService.getUserDetails();
        /*for(int userDto=0;userDto<userDtos.size();userDto++){
            userDtos.add(userService.getUserDetails());
        }*/
        return new ResponseEntity<List<User>>(userDtos,HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
   public ResponseEntity<UserDto> setUserDetails(@PathVariable("id") Long userId, @RequestBody SetUserRolesRequestDto request){
        UserDto userDto=userService.setUserDetails(userId,request.getRoleId());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
