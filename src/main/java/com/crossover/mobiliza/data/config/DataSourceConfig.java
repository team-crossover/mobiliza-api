package com.crossover.mobiliza.data.config;

import io.github.cdimascio.dotenv.Dotenv;
import javassist.NotFoundException;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * Configures the persistence data source.
 * It first looks for URL/username/password at the environment variables, which is required to work with Heroku.
 * If those aren't found, it will try to look for a ".env" file, which allows local testing/development.
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() throws NotFoundException {
        Dotenv dotenv = getDotenv();
        String dbUrl = getEnv(dotenv, "SPRING_DATASOURCE_URL");
        String username = getEnv(dotenv, "SPRING_DATASOURCE_USERNAME");
        String password = getEnv(dotenv, "SPRING_DATASOURCE_PASSWORD");

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }

    private Dotenv getDotenv() {
        try {
            String directory = "./";
            return Dotenv.configure()
                    .directory(directory)
                    .filename(".env")
                    .load();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getEnv(Dotenv dotenv, String name) throws NotFoundException {
        String env = null;
        env = System.getenv(name);
        if (env == null && dotenv != null)
            env = dotenv.get(name);
        if (env == null)
            throw new NotFoundException("Couldn't find environment variable: " + name);
        return env;
    }
}