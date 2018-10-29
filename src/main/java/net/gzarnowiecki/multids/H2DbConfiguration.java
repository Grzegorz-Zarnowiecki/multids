package net.gzarnowiecki.multids;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(basePackages = "net.gzarnowiecki.multids.H2.repository", entityManagerFactoryRef = "h2DSEmFactory", transactionManagerRef = "h2DSTransactionManager")

@PropertySource("classpath:application.yml")
public class H2DbConfiguration {
    @Value("${h2.datasource.hibernate-hbm2ddl-auto}")
    private String hbm2ddl;
    @Value("${h2.datasource.properties-hibernate-dialect}")
    private String hibernateDialect;

    @ConfigurationProperties("h2.datasource")
    @Bean
    public @Qualifier("h2.datasource")
    DataSourceProperties h2DSProperties() {

        return new DataSourceProperties();
    }


    @Bean
    public DataSource h2DS(@Qualifier("h2DSProperties") DataSourceProperties h2DSProperties) {
        return h2DSProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean h2DSEmFactory(@Qualifier("h2DS") DataSource h2DS, EntityManagerFactoryBuilder builder) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
        LocalContainerEntityManagerFactoryBean emf = builder.dataSource(h2DS).packages("net.gzarnowiecki.multids.H2.domain").build();
        emf.setJpaProperties(properties);
        emf.setPersistenceUnitName("h2Persistence");
        return emf;
    }

    @Bean
    public PlatformTransactionManager h2DSTransactionManager(@Qualifier("h2DSEmFactory") EntityManagerFactory h2DSEmFactory) {
        return new JpaTransactionManager(h2DSEmFactory);
    }

}

