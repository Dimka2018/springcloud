package com.epam.springcloud.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.epam.springcloud.resource.MessageBundle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SessionScope
@Component
@Entity
@Table(name = "CLIENT")
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LOGIN", unique = true)
    @NotNull(message = MessageBundle.EMPTY_LOGIN_MESSAGE)
    @Size(min = 3, max = 20, message = MessageBundle.INVALID_LOGIN_SIZE_MESSAGE)
    private String login;

    @Column(name = "PASSWORD")
    @NotNull(message = MessageBundle.EMPTY_PASSWORD_MESSAGE)
    @Size(min = 3, max = 20, message = MessageBundle.INVALID_PASSWORD_SIZE_MESSAGE)
    private String password;

    @Override
    public String toString() {
	return "User [id=" + id + ", login=" + login + ", password="
		+ (password != null ? "****" : password) + "]";
    }
}
