package io.taxi.live;

import io.taxi.live.testcontainers.PostgresContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class BaseIntegrationTest {

    private static final PostgreSQLContainer<?> postgres = PostgresContainer.getInstance();

    static {
        Startables.deepStart(postgres).join();
    }

}
