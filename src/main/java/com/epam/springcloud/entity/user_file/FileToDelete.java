package com.epam.springcloud.entity.user_file;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.epam.springcloud.resource.MessageBundle;

import lombok.Data;

@Data
@Entity
@Table(name = "USERFILE")
public class FileToDelete {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    @NotNull(message = MessageBundle.EMPTY_FILE_ID_MESSAGE)
    private Integer id;

    @Column(name = "USER_ID")
    private Integer userId;
}
