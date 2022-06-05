package com.dimka.springcloud.service.api;

import com.dimka.springcloud.dto.RegistrationRequest;
import com.dimka.springcloud.dto.Response;
import com.dimka.springcloud.entity.User;
import com.dimka.springcloud.mapper.RegistrationRequestToUserMapper;
import com.dimka.springcloud.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
