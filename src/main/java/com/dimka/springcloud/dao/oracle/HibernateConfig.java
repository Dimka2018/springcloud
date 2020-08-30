package com.dimka.springcloud.dao.oracle;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@AllArgsConstructor
@EnableTransactionManagement
public class HibernateConfig {

    private static final String DIALECT = "org.hibernate.dialect.Oracle10gDialect";
    private static final String ENCODING = "UTF-8";

    private final DataSource dataSource;

    @Bean("hibernateProperties")
    public Properties hibernateProperty() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", DIALECT);
        properties.setProperty("hibernate.connection.characterEncoding",
                ENCODING);
        return properties;
    }

    @Qualifier("hibernateProperties")
    @Autowired
    @Bean
    public LocalSessionFactoryBean sessionFactory(
            Properties hibernateProperties) {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPackagesToScan("com.dimka.springcloud.entity");
        bean.setHibernateProperties(hibernateProperties);
        return bean;
    }

    @Autowired
    @Bean
    public HibernateTransactionManager hibernateTransactionManager(
            SessionFactory factory) {
        return new HibernateTransactionManager(factory);
    }
}
