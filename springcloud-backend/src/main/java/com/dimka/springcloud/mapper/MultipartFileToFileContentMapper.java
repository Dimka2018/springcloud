package com.dimka.springcloud.mapper;

import com.dimka.springcloud.entity.FileContent;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartFileToFileContentMapper {

    @SneakyThrows
    public FileContent convert(MultipartFile file) {
        return new FileContent()
                .setContent(file.getBytes());
    }
}
