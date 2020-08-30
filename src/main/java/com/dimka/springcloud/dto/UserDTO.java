package com.dimka.springcloud.dto;

import com.dimka.springcloud.resource.MessageCode;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Scope("prototype")
@Component
public class UserDTO {

    @NotNull(message = MessageCode.EMPTY_LOGIN_MESSAGE)
    @Size(min = 3, max = 20, message = MessageCode.INVALID_LOGIN_SIZE_MESSAGE)
    private String login;

    @NotNull(message = MessageCode.EMPTY_PASSWORD_MESSAGE)
    @Size(min = 3, max = 20, message = MessageCode.INVALID_PASSWORD_SIZE_MESSAGE)
    private String password;

    @Override
    public String toString() {
        return "User [login=" + login + ", password="
                + (password != null ? "****" : password) + "]";
    }
}
