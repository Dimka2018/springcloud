package com.epam.springcloud.entity.file;

import lombok.Data;

@Data
public class FileToUserDTO {

    public FileToUserDTO() {

    }

    public FileToUserDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private Integer id;
    private String name;
}
