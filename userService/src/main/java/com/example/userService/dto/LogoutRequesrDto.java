package com.example.userService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequesrDto {
    private String token;
    private Long userId;
}
