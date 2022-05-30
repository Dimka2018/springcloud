package com.dimka.springcloud.mapper;

import com.dimka.springcloud.dto.RegistrationRequest;
import com.dimka.springcloud.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
public class RegistrationRequestToUserMapper {

    public User convert(RegistrationRequest request) {
        return new User()
                .setLogin(request.getLogin())
                .setPassword(request.getPassword());
    }
}
