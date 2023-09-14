package com.jb_cnsd.opdracht_2_3.web.controller;

import com.jb_cnsd.opdracht_2_3.data.models.Persoon;
import com.jb_cnsd.opdracht_2_3.data.repository.PersoonRepository;
import com.jb_cnsd.opdracht_2_3.data.repository.RekeningRepository;
import com.jb_cnsd.opdracht_2_3.web.dto.requests.PersoonCreateRequest;
import com.jb_cnsd.opdracht_2_3.web.dto.requests.PersoonEditRequest;
import com.jb_cnsd.opdracht_2_3.web.dto.responses.PersoonResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PersoonControllerIT {
    private final String P_BSN_1 = "123456789";
    private final String P_NAME_1 = "Bob";

    @LocalServerPort
    private int port;

    @Autowired
    PersoonRepository persoonRepository;

    @Autowired
    RekeningRepository rekeningRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        var p1 = new Persoon(P_BSN_1, P_NAME_1);
        persoonRepository.save(p1);
    }

    @AfterEach
    void tearDown() {
        persoonRepository.deleteAll();
        rekeningRepository.deleteAll();
    }

    @Test
    void getAll() throws URISyntaxException {
        // Arrange
        var uri = new URI(String.format("http://localhost:%d/persoon", port));

        // Act
        var response = restTemplate.getForEntity(uri, PersoonResponse[].class);

        // Assert
        assertTrue(response.hasBody());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        var p1 = Arrays.stream(response.getBody()).findFirst().orElseThrow();
        assertResponseBody(p1);
    }

    @Test
    void get() throws URISyntaxException {
        // Arrange
        var p1 = persoonRepository.findAll().get(0);
        var uri = new URI(String.format("http://localhost:%d/persoon/%d", port, p1.getId()));

        // Act
        var response = restTemplate.getForEntity(uri, PersoonResponse.class);

        // Assert
        assertTrue(response.hasBody());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertResponseBody(response.getBody());
    }

    @Test
    void getNotFound() throws URISyntaxException {
        // Arrange
        var uri = new URI(String.format("http://localhost:%d/persoon/0", port));

        // Act
        var response = restTemplate.getForEntity(uri, String.class);

        // Assert
        assertNotFoundResponse(response);
    }

    @Test
    void create() throws URISyntaxException {
        // Arrange
        var uri = new URI(String.format("http://localhost:%d/persoon", port));
        var requestBody = new PersoonCreateRequest("112233445", "Test");

        // Act
        var response = restTemplate.postForEntity(uri, requestBody, PersoonResponse.class);

        // Assert
        assertTrue(response.hasBody());
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        var body = response.getBody();
        assertTrue(body.id() > 0);
        assertEquals("112233445", body.bsn());
        assertEquals("Test", body.naam());
    }

    @Test
    void edit() throws URISyntaxException {
        // Arrange
        var p1 = persoonRepository.findAll().get(0);
        var uri = new URI(String.format("http://localhost:%d/persoon/%d", port, p1.getId()));
        var requestBody = new PersoonEditRequest("Test");

        // Act
        var response = restTemplate.exchange(new RequestEntity<>(requestBody, HttpMethod.PUT, uri), PersoonResponse.class);

        // Assert
        assertTrue(response.hasBody());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        var body = response.getBody();
        assertTrue(body.id() > 0);
        assertEquals(P_BSN_1, body.bsn());
        assertEquals("Test", body.naam());
    }

    @Test
    void editNotFound() throws URISyntaxException {
        // Arrange
        var uri = new URI(String.format("http://localhost:%d/persoon/0", port));
        var requestBody = new PersoonEditRequest("Test");

        // Act
        var response = restTemplate.exchange(new RequestEntity<>(requestBody, HttpMethod.PUT, uri), String.class);

        // Assert
        assertNotFoundResponse(response);
    }

    @Test
    void remove() throws URISyntaxException {
        // Arrange
        var p1 = persoonRepository.findAll().get(0);
        var uri = new URI(String.format("http://localhost:%d/persoon/%d", port, p1.getId()));

        // Act
        var response = restTemplate.exchange(uri, HttpMethod.DELETE, RequestEntity.EMPTY, Void.class);

        // Assert
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void removeNotFound() throws URISyntaxException {
        // Arrange
        var uri = new URI(String.format("http://localhost:%d/persoon/0", port));

        // Act
        var response = restTemplate.exchange(uri, HttpMethod.DELETE, RequestEntity.EMPTY, String.class);

        // Assert
        assertNotFoundResponse(response);
    }

    private void assertNotFoundResponse(ResponseEntity<String> response) {
        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertNotEquals(0, response.getBody().length());
    }

    private void assertResponseBody(PersoonResponse persoon) {
        assertTrue(persoon.id() > 0);
        assertEquals(P_BSN_1, persoon.bsn());
        assertEquals(P_NAME_1, persoon.naam());
    }
}