package valeron.bondar.config;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${spring.datasource.driver-class-name}")
    private String dsClassName;
    @Value("${spring.datasource.username}")
    private String dsUsername;
    @Value("${spring.datasource.password}")
    private String dsPassword;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String dsStrategy;
    @Value("${spring.datasource.url}")
    private String dsUrl;

    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_XML);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("PUT", "GET", "DELETE", "POST", "PATCH");
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(dsClassName);
        dataSource.setUsername(dsUsername);
        dataSource.setPassword(dsPassword);
        dataSource.setUrl(dsUrl);
        Properties properties = new Properties();
        properties.setProperty("spring.jpa.hibernate.ddl-auto", dsStrategy);
        dataSource.setConnectionProperties(properties);

        return dataSource;
    }

}
