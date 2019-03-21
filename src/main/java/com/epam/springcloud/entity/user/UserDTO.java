package com.epam.springcloud.entity.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.springcloud.resource.MessageBundle;

import lombok.Data;

@Data
@Scope("prototype")
@Component
public class UserDTO {

    @NotNull(message = MessageBundle.EMPTY_LOGIN_MESSAGE)
    @Size(min = 3, max = 20, message = MessageBundle.INVALID_LOGIN_SIZE_MESSAGE)
    private String login;

    @NotNull(message = MessageBundle.EMPTY_PASSWORD_MESSAGE)
    @Size(min = 3, max = 20, message = MessageBundle.INVALID_PASSWORD_SIZE_MESSAGE)
    private String password;

    @Override
    public String toString() {
        return "User [login=" + login + ", password="
                + (password != null ? "****" : password) + "]";
    }
}
