package com.jb_cnsd.opdracht_3_4.web.controller;

import com.jb_cnsd.opdracht_3_4.domain.external.DummyEmployeeResponse;
import com.jb_cnsd.opdracht_3_4.domain.external.DummyEmployeesResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DummyApiControllerIT {
    @LocalServerPort
    private int port;
    private final int mockServerPort = 9090;

    @Autowired
    private TestRestTemplate restTemplate;

    private ClientAndServer mockServer;

    @BeforeEach
    void SetUp() {
        mockServer = ClientAndServer.startClientAndServer(mockServerPort);
    }

    @AfterEach
    void TearDown() {
        mockServer.stop();
    }

    @Test
    void getAll() throws IOException, URISyntaxException {
        // Arrange
        final String mockedApiMethod = "GET";
        final String mockedApiUri = "/api/v1/employees";
        final var mockServerClient = new MockServerClient("localhost", mockServerPort);
        mockServerClient
                .when(
                        request()
                                .withMethod(mockedApiMethod)
                                .withPath(mockedApiUri),
                        exactly(1)
                ).respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                                .withDelay(TimeUnit.SECONDS, 1)
                                .withBody(Files.readString(Path.of(getClass().getResource("/apiResponses/dummyEmployees.json").toURI())))
                );

        // Act
        var uri = new URI(String.format("http://localhost:%d/dummy-api", port));
        var response = restTemplate.exchange(uri, HttpMethod.GET, RequestEntity.EMPTY, DummyEmployeesResponse.class);

        // Assert
        mockServerClient.verify(
                request()
                        .withMethod(mockedApiMethod)
                        .withPath(mockedApiUri),
                VerificationTimes.exactly(1)
        );
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        final HttpRequest[] httpRequests = mockServerClient.retrieveRecordedRequests(request());
        assertEquals(1, httpRequests.length);
        final HttpRequest httpRequest = httpRequests[0];
        assertEquals(mockedApiMethod, httpRequest.getMethod().getValue());
        assertEquals(mockedApiUri, httpRequest.getPath().getValue());
    }

    @Test
    void get() throws IOException, URISyntaxException {
        // Arrange
        final String mockedApiMethod = "GET";
        final String mockedApiUri = "/api/v1/employee/1";
        final var mockServerClient = new MockServerClient("localhost", mockServerPort);
        mockServerClient
                .when(
                        request()
                                .withMethod(mockedApiMethod)
                                .withPath(mockedApiUri),
                        exactly(1)
                ).respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"))
                                .withDelay(TimeUnit.SECONDS, 1)
                                .withBody(Files.readString(Path.of(getClass().getResource("/apiResponses/dummyEmployee.json").toURI())))
                );

        // Act
        var uri = new URI(String.format("http://localhost:%d/dummy-api/1", port));
        var response = restTemplate.exchange(uri, HttpMethod.GET, RequestEntity.EMPTY, DummyEmployeeResponse.class);

        // Assert
        mockServerClient.verify(
                request()
                        .withMethod(mockedApiMethod)
                        .withPath(mockedApiUri),
                VerificationTimes.exactly(1)
        );
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        final HttpRequest[] httpRequests = mockServerClient.retrieveRecordedRequests(request());
        assertEquals(1, httpRequests.length);
        final HttpRequest httpRequest = httpRequests[0];
        assertEquals(mockedApiMethod, httpRequest.getMethod().getValue());
        assertEquals(mockedApiUri, httpRequest.getPath().getValue());
    }
}