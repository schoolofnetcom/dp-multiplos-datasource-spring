package com.example.datasource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "personEntityManagerFactory",
        transactionManagerRef = "personTransactionManager", basePackages = {"com.example.datasource.person"})
public class PersonDTConfig {

    @Primary
    @Bean(name = "personDataSource")
    @ConfigurationProperties(prefix = "database1.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "personEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean personEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("personDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.example.datasource.person").persistenceUnit("person")
                .build();
    }

    @Primary
    @Bean(name = "personTransactionManager")
    public PlatformTransactionManager personTransactionManager(
            @Qualifier("personEntityManagerFactory") EntityManagerFactory personEntityManagerFactory) {
        return new JpaTransactionManager(personEntityManagerFactory);
    }
}
