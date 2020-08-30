package com.dimka.springcloud.entity;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.*;
import java.io.Serializable;

@Data
@SessionScope
@Component
@Entity
@Table(name = "CLIENT",  schema="SKYCLOUD")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public User() {

    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_SEQ")
    @SequenceGenerator(name="CLIENT_SEQ", sequenceName="CLIENT_SEQ", allocationSize=1)
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
