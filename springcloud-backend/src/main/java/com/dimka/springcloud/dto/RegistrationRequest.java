package com.dimka.springcloud.dto;

import lombok.Data;

@Data
public class RegistrationRequest {

    private String login;
    private String password;
}
