package com.dimka.springcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileToUserDTO {
    private Integer id;
    private String name;
}
