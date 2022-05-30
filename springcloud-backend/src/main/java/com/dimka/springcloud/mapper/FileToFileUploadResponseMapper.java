package com.dimka.springcloud.mapper;

import com.dimka.springcloud.dto.FileUploadResponse;
import com.dimka.springcloud.entity.File;
import org.springframework.stereotype.Component;

@Component
public class FileToFileUploadResponseMapper {

    public FileUploadResponse convert(File file) {
        return new FileUploadResponse()
                .setId(file.getId())
                .setName(file.getName());
    }
}
