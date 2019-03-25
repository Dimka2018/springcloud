package com.epam.springcloud.entity.user_file;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.epam.springcloud.resource.MessageBundle;

import lombok.Data;

@Data
public class FileUploadDTO {

    @NotNull(message = MessageBundle.EMPTY_FILE_NAME_MESSAGE)
    @Size(min = 1, max = 250, message = MessageBundle.INVALID_FILE_NAME_SIZE_MESSAGE)
    private String name;

    @NotNull(message = MessageBundle.EMPTY_FILE_CONTENT_MESSAGE)
    private MultipartFile content;

}
