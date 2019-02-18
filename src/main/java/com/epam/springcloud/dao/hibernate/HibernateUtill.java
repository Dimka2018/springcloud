package com.epam.springcloud.dao.hibernate;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2

public class HibernateUtill {

    private SessionFactory factory;

    public Session getSession() {
	log.debug("open session");
	return factory.openSession();
    }

    @PostConstruct
    private void init() {
	Locale.setDefault(Locale.ENGLISH);
	log.debug("Configure standart service registry");
	StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		.configure() // configures settings from hibernate.cfg.xml
		.build();
	log.debug("build session factory");
	factory = new MetadataSources(registry).buildMetadata()
		.buildSessionFactory();

    }

    @PreDestroy
    private void destroy() {
	log.debug("close session factory");
	factory.close();
    }
}
