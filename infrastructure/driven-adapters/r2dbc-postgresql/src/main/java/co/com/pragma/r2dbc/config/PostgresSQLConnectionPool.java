package co.com.pragma.r2dbc.config;

import co.com.pragma.r2dbc.config.PostgresSQLConnectionProperties;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class PostgresSQLConnectionPool {
    /* Change these values for your project */
    public static final int INITIAL_SIZE = 12;
    public static final int MAX_SIZE = 15;
    public static final int MAX_IDLE_TIME = 30;
    public static final int DEFAULT_PORT = 5432;

    @Bean
    public ConnectionPool getConnectionConfig(PostgresSQLConnectionProperties properties) {
        log.info("Conectando a Postgres R2DBC en {}:{}/{} con usuario {}",
              properties.host(), properties.port(), properties.database(), properties.username());
        PostgresqlConnectionConfiguration dbConfiguration = PostgresqlConnectionConfiguration.builder()
              .host(properties.host())
              .port(properties.port())
              .database(properties.database())
              .schema(properties.schema())
              .username(properties.username())
              .password(properties.password())
              .build();

        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder()
              .connectionFactory(new PostgresqlConnectionFactory(dbConfiguration))
              .name("api-postgres-connection-pool")
              .initialSize(INITIAL_SIZE)
              .maxSize(MAX_SIZE)
              .maxIdleTime(Duration.ofMinutes(MAX_IDLE_TIME))
              .validationQuery("SELECT 1")
              .build();

        return new ConnectionPool(poolConfiguration);
    }
}