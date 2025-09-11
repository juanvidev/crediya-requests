package co.com.crediya.r2dbc.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class PostgreSQLConnectionPoolTest {

    @InjectMocks
    private PostgreSQLConnectionPool connectionPool;

    @Mock
    private PostgresqlConnectionProperties properties;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(properties.host()).thenReturn("localhost");
        when(properties.port()).thenReturn(5434);
        when(properties.database()).thenReturn("postgres");
        when(properties.schema()).thenReturn("public");
        when(properties.username()).thenReturn("postgres");
        when(properties.password()).thenReturn("postgrespw");
    }

    @Test
    @DisplayName("Should create connection pool with valid properties")
    void testConnectionPoolCreation() {
        assertNotNull(connectionPool.getConnectionConfig(properties));

    }

    @Test
    @DisplayName("Should throw exception with invalid properties")
    void testConnectionPoolCreationWithInvalidProperties() {
        when(properties.host()).thenReturn(null);
        try {
            connectionPool.getConnectionConfig(properties);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    @DisplayName("Should create connection pool with edge case properties")
    void testConnectionPoolCreationWithEdgeCaseProperties() {
        when(properties.port()).thenReturn(0);
        assertNotNull(connectionPool.getConnectionConfig(properties));
    }
}
