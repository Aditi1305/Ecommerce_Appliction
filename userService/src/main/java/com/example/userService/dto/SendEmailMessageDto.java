package com.example.userService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailMessageDto {
    private String to;
    private String from;
    private String subject;
    private String body;
}
