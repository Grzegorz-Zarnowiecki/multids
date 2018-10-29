package net.gzarnowiecki.multids;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "net.gzarnowiecki.multids.HSQLDB.repository", entityManagerFactoryRef = "hsqldbDSEmFactory", transactionManagerRef = "hsqldbDSTransactionManager")
@PropertySource("classpath:application.yml")
@ConfigurationProperties("hsqldb.datasource")
public class HsqlDbConfiguration {

    @Value("${hsqldb.datasource.hibernate-hbm2ddl-auto}")
    private String hbm2ddl;
    @Value("${hsqldb.datasource.properties-hibernate-dialect}")
    private String hibernateDialect;

    @Bean


    public @Qualifier("hsqldb.datasource")
    DataSourceProperties hsqldbDSProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource hsqldbDS(@Qualifier("hsqldbDSProperties") DataSourceProperties hsqldbDSProperties) {
        return hsqldbDSProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean hsqldbDSEmFactory(@Qualifier("hsqldbDS") DataSource hsqldbDS, EntityManagerFactoryBuilder builder) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
        LocalContainerEntityManagerFactoryBean emf = builder.dataSource(hsqldbDS).packages("net.gzarnowiecki.multids.hsqldb.domain").build();
        emf.setJpaProperties(properties);
        emf.setPersistenceUnitName("hsqldbPersistence");
        return emf;
    }

    @Primary
    @Bean
    public PlatformTransactionManager hsqldbDSTransactionManager(@Qualifier("hsqldbDSEmFactory") EntityManagerFactory hsqldbDSEmFactory) {
        return new JpaTransactionManager(hsqldbDSEmFactory);
    }
}
