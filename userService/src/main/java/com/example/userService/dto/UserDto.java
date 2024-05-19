package com.example.userService.dto;

import com.example.userService.models.Role;
import com.example.userService.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String email;
    private Set<Role> roles=new HashSet<>();
    private Long id;
    public static UserDto from(User user){
        UserDto userDto=new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
