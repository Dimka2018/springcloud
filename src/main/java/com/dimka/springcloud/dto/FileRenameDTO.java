package com.dimka.springcloud.dto;

import com.dimka.springcloud.resource.MessageCode;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class FileRenameDTO {

    @NotNull(message = MessageCode.EMPTY_FILE_ID_MESSAGE)
    private Integer id;

    @NotNull(message = MessageCode.EMPTY_FILE_NAME_MESSAGE)
    @Size(min = 1, max = 250, message = MessageCode.INVALID_FILE_NAME_SIZE_MESSAGE)
    @Pattern(regexp = "[^\\/:*?|]*", message = MessageCode.INVALID_FILE_NAME_MESSAGE)
    private String name;
}
