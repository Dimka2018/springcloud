package com.epam.springcloud.entity.user_file;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.epam.springcloud.resource.MessageBundle;

import lombok.Data;

@Data
public class FileRenameDTO {

    @NotNull(message = MessageBundle.EMPTY_FILE_ID_MESSAGE)
    private Integer id;

    @NotNull(message = MessageBundle.EMPTY_FILE_NAME_MESSAGE)
    @Size(min = 1, max = 250, message = MessageBundle.INVALID_FILE_NAME_SIZE_MESSAGE)
    @Pattern(regexp = "[^\\/:*?|]*", message = MessageBundle.INVALID_FILE_NAME_MESSAGE)
    private String name;
}
