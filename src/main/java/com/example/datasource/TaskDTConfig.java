package com.example.datasource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "taskEntityManagerFactory",
        transactionManagerRef = "taskTransactionManager", basePackages = {"com.example.datasource.task"})
public class TaskDTConfig {

    @Bean(name = "taskDataSource")
    @ConfigurationProperties(prefix = "database2.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "taskEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean taskEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("taskDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.example.datasource.task").persistenceUnit("task")
                .build();
    }

    @Bean(name = "taskTransactionManager")
    public PlatformTransactionManager taskTransactionManager(
            @Qualifier("taskEntityManagerFactory") EntityManagerFactory taskEntityManagerFactory) {
        return new JpaTransactionManager(taskEntityManagerFactory);
    }
}
