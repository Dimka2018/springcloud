package com.dimka.springcloud.service.api;

import com.dimka.springcloud.dto.LoginRequest;
import com.dimka.springcloud.dto.RegistrationRequest;
import com.dimka.springcloud.dto.Response;
import com.dimka.springcloud.entity.User;
import com.dimka.springcloud.mapper.RegistrationRequestToUserMapper;
import com.dimka.springcloud.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserApiService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final RegistrationRequestToUserMapper createUserRequestToUserMapper;

    public Response createUser(RegistrationRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        User user = createUserRequestToUserMapper.convert(request);
        Response response = new Response()
                .setSuccess(true);
        try {
            userService.save(user);
        } catch (Exception e) {
            log.info("Can't create user {}", user, e);
            response.setSuccess(false);
            response.setMessage("User already exists");
        }
        return response;
    }

    public Response login(LoginRequest loginRequest, HttpServletRequest request) {
        Response response = new Response()
                .setSuccess(true);
        Optional<User> user = userService.find(loginRequest.getLogin(),loginRequest.getPassword());
        if (user.isPresent()) {
            request.getSession().setAttribute("user", user.get());
        } else {
            response.setSuccess(false)
                    .setMessage("Invalid login or password");
        }
        return response;
    }

    public void logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();
    }
}
