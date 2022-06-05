package com.dimka.springcloud.controller;

import com.dimka.springcloud.dto.RegistrationRequest;
import com.dimka.springcloud.dto.Response;
import com.dimka.springcloud.dto.LoginRequest;
import com.dimka.springcloud.service.api.UserApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserApiService userApiService;

    @PostMapping("/api/user")
    public Response registerUser(@RequestBody RegistrationRequest user) {
        return userApiService.createUser(user);
    }
}
