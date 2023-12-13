package com.foundation.crud.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class TestContainerTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.1")
            .withDatabaseName("dyesfi-dao-unit-test")
            .withUsername("byesfi")
            .withPassword("password");

    @Test
    void canStartPostgresDB() {
        Assertions.assertThat(postgreSQLContainer.isRunning()).isTrue();
        Assertions.assertThat(postgreSQLContainer.isCreated()).isTrue();
    }
}
