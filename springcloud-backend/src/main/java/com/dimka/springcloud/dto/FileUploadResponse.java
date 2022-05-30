package com.dimka.springcloud.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class FileUploadResponse {

    private Long id;
    private String name;
}
