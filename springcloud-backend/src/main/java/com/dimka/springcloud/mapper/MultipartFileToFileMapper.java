package com.dimka.springcloud.mapper;

import com.dimka.springcloud.entity.File;
import com.dimka.springcloud.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartFileToFileMapper {

    public File convert(MultipartFile file, User user) {
        return new File()
                .setName(file.getOriginalFilename())
                .setUserId(user.getId());
    }
}
