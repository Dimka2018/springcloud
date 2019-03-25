package com.epam.springcloud.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Data
@SessionScope
@Component
@Entity
@Table(name = "CLIENT")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public User() {

    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LOGIN", unique = true)
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + login + ", password="
                + (password != null ? "****" : password) + "]";
    }

}
