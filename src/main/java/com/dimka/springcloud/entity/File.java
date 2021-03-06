package com.dimka.springcloud.entity;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Data
@Scope("prototype")
@Component
@Entity
@Table(name = "USERFILE", schema = "SKYCLOUD")
public class File {

    public File() {

    }

    public File(String name, MultipartFile content, Integer userId) {
        this.name = name;
        this.content = content;
        this.userId = userId;
    }

    public File(Integer id, String name, Integer userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public File(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERFILE_SEQ")
    @SequenceGenerator(name="USERFILE_SEQ", sequenceName="USERFILE_SEQ", allocationSize=1)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "PATH")
    private String path;

    @Transient
    private MultipartFile content;
}
