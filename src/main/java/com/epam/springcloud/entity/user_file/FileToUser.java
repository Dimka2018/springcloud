package com.epam.springcloud.entity.user_file;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.epam.springcloud.resource.MessageBundle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Component
@Scope("prototype")
@Entity
@Table(name = "USERFILE")
public class FileToUser {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    @NotNull(message = MessageBundle.EMPTY_FILE_ID_MESSAGE)
    private Integer id;

    @Column(name = "name")
    @NotNull(message = MessageBundle.EMPTY_FILE_NAME_MESSAGE)
    @Size(min = 1, max = 250, message = MessageBundle.INVALID_FILE_NAME_SIZE_MESSAGE)
    @Pattern(regexp = "[^\\/:*?|]*", message = MessageBundle.INVALID_FILE_NAME_MESSAGE)
    private String name;

    @Column(name = "USER_ID")
    private Integer userId;

    public FileToUser(FileToUpload file) {
	this.id = file.getId();
	this.name = file.getName();
	this.userId = file.getUserId();
    }
}
