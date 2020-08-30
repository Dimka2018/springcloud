package com.dimka.springcloud.dto;

import com.dimka.springcloud.resource.MessageCode;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FileUploadDTO {

    @NotNull(message = MessageCode.EMPTY_FILE_NAME_MESSAGE)
    @Size(min = 1, max = 250, message = MessageCode.INVALID_FILE_NAME_SIZE_MESSAGE)
    private String name;

    @NotNull(message = MessageCode.EMPTY_FILE_CONTENT_MESSAGE)
    private MultipartFile content;

}
