package co.com.crediya.r2dbc.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PostgresSQLConnectionPoolPropertiesTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(TestConfig.class)
            .withPropertyValues(
                    "adapters.r2dbc.host=localhost",
                    "adapters.r2dbc.port=5434",
                    "adapters.r2dbc.database=postgres",
                    "adapters.r2dbc.schema=public",
                    "adapters.r2dbc.username=postgres",
                    "adapters.r2dbc.password=postgrespw"
            );


    @Test
    @DisplayName("Should bind PostgreSQL connection properties correctly from configuration")
    void testPropertiesBinding() {
        contextRunner.run(context -> {
            PostgresqlConnectionProperties props = context.getBean(PostgresqlConnectionProperties.class);
            assertThat(props.host()).isEqualTo("localhost");
            assertThat(props.port()).isEqualTo(5434);
            assertThat(props.database()).isEqualTo("postgres");
            assertThat(props.schema()).isEqualTo("public");
            assertThat(props.username()).isEqualTo("postgres");
            assertThat(props.password()).isEqualTo("postgrespw");
        });
    }

    @EnableConfigurationProperties(PostgresqlConnectionProperties.class)
    static class TestConfig {
    }
}
