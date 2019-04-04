package com.epam.springcloud.entity.file;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.epam.springcloud.resource.MessageKey;

import lombok.Data;

@Data
public class FileUploadDTO {

    @NotNull(message = MessageKey.EMPTY_FILE_NAME_MESSAGE)
    @Size(min = 1, max = 250, message = MessageKey.INVALID_FILE_NAME_SIZE_MESSAGE)
    private String name;

    @NotNull(message = MessageKey.EMPTY_FILE_CONTENT_MESSAGE)
    private MultipartFile content;

}
