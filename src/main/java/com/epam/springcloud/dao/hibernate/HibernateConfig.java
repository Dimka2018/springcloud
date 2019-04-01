package com.epam.springcloud.dao.hibernate;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    private static final String DRIVER_CLASS_NAME = "oracle.jdbc.OracleDriver";
    private static final String DIALECT = "org.hibernate.dialect.Oracle10gDialect";
    private static final String ENCODING = "UTF-8";
    private static final String DB_URL = "jdbc:oracle:thin:SKYCLOUD@//localhost:1521/xe";
    private static final String DB_USER = "SKYCLOUD";
    private static final String DB_PASS = "root";

    @Bean
    public BasicDataSource basicDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASS);
        return dataSource;
    }

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
            javax.sql.DataSource dataSource, Properties hibernateProperties) {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPackagesToScan("com.epam.springcloud.entity");
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
