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

import com.epam.springcloud.entity.user.User;
import com.epam.springcloud.entity.user_file.FileToDelete;
import com.epam.springcloud.entity.user_file.FileToDownload;
import com.epam.springcloud.entity.user_file.FileToUpload;
import com.epam.springcloud.entity.user_file.FileToUser;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Bean
    public BasicDataSource basicDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:SKYCLOUD@//localhost:1521/xe");
        dataSource.setUsername("SKYCLOUD");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean("hibernateProperties")
    public Properties hibernateProperty() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect",
                "org.hibernate.dialect.Oracle10gDialect");
        return properties;
    }

    @Qualifier("hibernateProperties")
    @Autowired
    @Bean
    public LocalSessionFactoryBean sessionFactory(
            javax.sql.DataSource dataSource, Properties hibernateProperties) {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setAnnotatedClasses(User.class, FileToDelete.class, FileToDownload.class, FileToUpload.class, FileToUser.class);
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
