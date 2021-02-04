package cn.bluetech.gragas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author xu
 * @date 2019/8/20 16:50
 */
@Configuration
@EnableJpaRepositories(
        basePackages = {
                "cn.bluetech",
                "com.brframework"
        }
)
public class DefaultDataSourceConfig {

    @Autowired
    JpaProperties jpaProperties;

    @Autowired
    HibernateProperties hibernateProperties;

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            EntityManagerFactoryBuilder builder
            , DataSource dataSource
    ) {
        Map<String, Object> properties = hibernateProperties
                .determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        return builder
                .dataSource(dataSource)
                .packages("cn.bluetech",
                        "com.brframework")
                .persistenceUnit("default")
                .properties(properties)
                .build();
    }

}
