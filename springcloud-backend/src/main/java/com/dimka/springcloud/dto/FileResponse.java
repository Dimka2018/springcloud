package com.dimka.springcloud.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class FileResponse {

    private Long id;
    private String name;
}
