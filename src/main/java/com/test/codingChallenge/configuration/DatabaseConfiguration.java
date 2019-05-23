package com.test.codingChallenge.configuration;

import org.hibernate.SessionFactory;
import org.hsqldb.jdbc.JDBCDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Bean
    public HibernateTemplate hibernateTemplate() {
        return new HibernateTemplate(sessionFactory());
    }

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(getDataSource());
        builder.scanPackages("com.test.codingChallenge.dao");
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "create");
        builder.addProperties(properties);
        return builder.buildSessionFactory();
    }

    @Bean
    public DataSource getDataSource() {
        DataSource dataSource = new JDBCDataSource();
        ((JDBCDataSource) dataSource).setDatabase("org.hsqldb.jdbc.JDBCDriver");
        ((JDBCDataSource) dataSource).setUrl("jdbc:hsqldb:hsql://localhost/alert");
        ((JDBCDataSource) dataSource).setUser("sa");
        ((JDBCDataSource) dataSource).setPassword("");
        return dataSource;
    }
}
