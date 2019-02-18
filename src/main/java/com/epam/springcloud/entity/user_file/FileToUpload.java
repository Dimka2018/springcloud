package com.epam.springcloud.entity.user_file;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import com.epam.springcloud.resource.MessageBundle;

import lombok.Data;

@Data
@RequestScope
@Component
@Entity
@Table(name = "USERFILE")
public class FileToUpload {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    @NotNull(message = MessageBundle.EMPTY_FILE_NAME_MESSAGE)
    @Size(min = 1, max = 250, message = MessageBundle.INVALID_FILE_NAME_SIZE_MESSAGE)
    private String name;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "PATH")
    private String path;

    @Transient
    @NotNull(message = MessageBundle.EMPTY_FILE_CONTENT_MESSAGE)
    private MultipartFile content;

}
