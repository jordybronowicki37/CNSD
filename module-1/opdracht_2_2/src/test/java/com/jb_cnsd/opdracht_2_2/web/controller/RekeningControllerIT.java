package com.jb_cnsd.opdracht_2_2.web.controller;

import com.jb_cnsd.opdracht_2_2.data.models.Iban;
import com.jb_cnsd.opdracht_2_2.data.models.Persoon;
import com.jb_cnsd.opdracht_2_2.data.models.Rekening;
import com.jb_cnsd.opdracht_2_2.data.models.RekeningStatus;
import com.jb_cnsd.opdracht_2_2.data.repository.PersoonRepository;
import com.jb_cnsd.opdracht_2_2.data.repository.RekeningRepository;
import com.jb_cnsd.opdracht_2_2.web.dto.requests.RekeningCreateRequest;
import com.jb_cnsd.opdracht_2_2.web.dto.requests.RekeningEditRequest;
import com.jb_cnsd.opdracht_2_2.web.dto.requests.SaldoChangeRequest;
import com.jb_cnsd.opdracht_2_2.web.dto.responses.RekeningResponse;
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

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RekeningControllerIT {
    private final String P_BSN_1 = "123456789";
    private final String P_NAME_1 = "Bob";
    private final String R_IBAN_1 = "NL99CNSD1122334455";

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
        var r1 = new Rekening(new Iban(R_IBAN_1), p1);
        r1.setSaldo(100);
        rekeningRepository.save(r1);
    }

    @AfterEach
    void tearDown() {
        rekeningRepository.deleteAll();
        persoonRepository.deleteAll();
    }

    @Test
    void getAll() throws URISyntaxException {
        // Arrange
        var uri = new URI(String.format("http://localhost:%d/rekening", port));

        // Act
        var response = restTemplate.getForEntity(uri, RekeningResponse[].class);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.hasBody());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertStandardResponseBody(response.getBody()[0]);
    }

    @Test
    void get() throws URISyntaxException {
        // Arrange
        var r1 = rekeningRepository.findAll().get(0);
        var uri = new URI(String.format("http://localhost:%d/rekening/%d", port, r1.getId()));

        // Act
        var response = restTemplate.getForEntity(uri, RekeningResponse.class);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.hasBody());
        assertNotNull(response.getBody());
        assertStandardResponseBody(response.getBody());
    }

    @Test
    void getNotFound() throws URISyntaxException {
        // Arrange
        var uri = new URI(String.format("http://localhost:%d/rekening/0", port));

        // Act
        var response = restTemplate.exchange(uri, HttpMethod.GET, RequestEntity.EMPTY, String.class);

        // Assert
        assertNotFoundResponse(response);
    }

    @Test
    void create() throws URISyntaxException {
        // Arrange
        var p1 = persoonRepository.findAll().get(0);
        var uri = new URI(String.format("http://localhost:%d/rekening", port));
        var requestBody = new RekeningCreateRequest(p1.getId());

        // Act
        var response = restTemplate.postForEntity(uri, requestBody, RekeningResponse.class);

        // Assert
        assertTrue(response.hasBody());
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        var body = response.getBody();
        assertTrue(body.id() > 0);
        assertEquals(18, body.iban().length());
        assertTrue(body.iban().startsWith("NL99CNSD"));
        assertEquals(RekeningStatus.NORMAAL, body.status());
        assertEquals(0, body.saldo());
        assertEquals(1, body.personen().size());
        assertEquals(p1.getId(), body.personen().get(0));
    }

    @Test
    void edit() throws URISyntaxException {
        // Arrange
        var p1 = persoonRepository.findAll().get(0);
        var r1 = rekeningRepository.findAll().get(0);
        var uri = new URI(String.format("http://localhost:%d/rekening/%d", port, r1.getId()));
        var requestBody = new RekeningEditRequest(RekeningStatus.GEBLOKKEERD);

        // Act
        var response = restTemplate.exchange(new RequestEntity<>(requestBody, HttpMethod.PUT, uri), RekeningResponse.class);

        // Assert
        assertTrue(response.hasBody());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        var body = response.getBody();
        assertTrue(body.id() > 0);
        assertEquals(18, body.iban().length());
        assertTrue(body.iban().startsWith("NL99CNSD"));
        assertEquals(RekeningStatus.GEBLOKKEERD, body.status());
        assertEquals(100, body.saldo());
        assertEquals(1, body.personen().size());
        assertEquals(p1.getId(), body.personen().get(0));
    }

    @Test
    void editNotFound() throws URISyntaxException {
        // Arrange
        var uri = new URI(String.format("http://localhost:%d/rekening/0", port));
        var requestBody = new RekeningEditRequest(RekeningStatus.GEBLOKKEERD);

        // Act
        var response = restTemplate.exchange(new RequestEntity<>(requestBody, HttpMethod.PUT, uri) , String.class);

        // Assert
        assertNotFoundResponse(response);
    }

    @Test
    void remove() throws URISyntaxException {
        // Arrange
        var r1 = rekeningRepository.findAll().get(0);
        var uri = new URI(String.format("http://localhost:%d/rekening/%d", port, r1.getId()));

        // Act
        var response = restTemplate.exchange(uri, HttpMethod.DELETE, RequestEntity.EMPTY, Void.class);

        // Assert
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    void removeNotFound() throws URISyntaxException {
        // Arrange
        var uri = new URI(String.format("http://localhost:%d/rekening/0", port));

        // Act
        var response = restTemplate.exchange(uri, HttpMethod.DELETE, RequestEntity.EMPTY, String.class);

        // Assert
        assertNotFoundResponse(response);
    }

    @Test
    void addPersoon() throws URISyntaxException {
        // Arrange
        var r1 = rekeningRepository.findAll().get(0);
        var p1 = persoonRepository.findAll().get(0);
        var p2 = new Persoon("112233445", "Test");
        persoonRepository.save(p2);
        var uri = new URI(String.format("http://localhost:%d/rekening/%d/persoon/%d", port, r1.getId(), p2.getId()));

        // Act
        var response = restTemplate.exchange(new RequestEntity<>(HttpMethod.POST, uri), RekeningResponse.class);

        // Assert
        assertTrue(response.hasBody());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        var body = response.getBody();
        assertTrue(body.id() > 0);
        assertEquals(18, body.iban().length());
        assertTrue(body.iban().startsWith("NL99CNSD"));
        assertEquals(RekeningStatus.NORMAAL, body.status());
        assertEquals(100, body.saldo());
        assertEquals(2, body.personen().size());
        assertTrue(body.personen().contains(p1.getId()));
        assertTrue(body.personen().contains(p2.getId()));
    }

    @Test
    void removePersoon() throws URISyntaxException {
        // Arrange
        var r1 = rekeningRepository.findAll().get(0);
        var p1 = persoonRepository.findAll().get(0);
        var p2 = new Persoon("112233445", "Test");
        persoonRepository.save(p2);
        r1.getPersonen().add(p2);
        rekeningRepository.save(r1);
        var uri = new URI(String.format("http://localhost:%d/rekening/%d/persoon/%d", port, r1.getId(), p1.getId()));

        // Act
        var response = restTemplate.exchange(new RequestEntity<>(HttpMethod.DELETE, uri), RekeningResponse.class);

        // Assert
        assertTrue(response.hasBody());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        var body = response.getBody();
        assertTrue(body.id() > 0);
        assertEquals(18, body.iban().length());
        assertTrue(body.iban().startsWith("NL99CNSD"));
        assertEquals(RekeningStatus.NORMAAL, body.status());
        assertEquals(100, body.saldo());
        assertEquals(1, body.personen().size());
        assertFalse(body.personen().contains(p1.getId()));
        assertTrue(body.personen().contains(p2.getId()));
    }

    @Test
    void addSaldo() throws URISyntaxException {
        // Arrange
        var r1 = rekeningRepository.findAll().get(0);
        var p1 = persoonRepository.findAll().get(0);
        var uri = new URI(String.format("http://localhost:%d/rekening/%d/saldo", port, r1.getId()));
        var requestBody = new SaldoChangeRequest(100);

        // Act
        var response = restTemplate.exchange(new RequestEntity<>(requestBody, HttpMethod.POST, uri), RekeningResponse.class);

        // Assert
        assertTrue(response.hasBody());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        var body = response.getBody();
        assertTrue(body.id() > 0);
        assertEquals(18, body.iban().length());
        assertTrue(body.iban().startsWith("NL99CNSD"));
        assertEquals(RekeningStatus.NORMAAL, body.status());
        assertEquals(200, body.saldo());
        assertEquals(1, body.personen().size());
        assertTrue(body.personen().contains(p1.getId()));
    }

    @Test
    void removeSaldo() throws URISyntaxException {
        // Arrange
        var r1 = rekeningRepository.findAll().get(0);
        var p1 = persoonRepository.findAll().get(0);
        var uri = new URI(String.format("http://localhost:%d/rekening/%d/saldo", port, r1.getId()));
        var requestBody = new SaldoChangeRequest(50);

        // Act
        var response = restTemplate.exchange(new RequestEntity<>(requestBody, HttpMethod.DELETE, uri), RekeningResponse.class);

        // Assert
        assertTrue(response.hasBody());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        var body = response.getBody();
        assertTrue(body.id() > 0);
        assertEquals(18, body.iban().length());
        assertTrue(body.iban().startsWith("NL99CNSD"));
        assertEquals(RekeningStatus.NORMAAL, body.status());
        assertEquals(50, body.saldo());
        assertEquals(1, body.personen().size());
        assertTrue(body.personen().contains(p1.getId()));
    }

    private void assertNotFoundResponse(ResponseEntity<String> response) {
        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertNotEquals(0, response.getBody().length());
    }

    private void assertStandardResponseBody(RekeningResponse rekening) {
        assertTrue(rekening.id() > 0);
        assertEquals(R_IBAN_1, rekening.iban());
        assertEquals(RekeningStatus.NORMAAL, rekening.status());
        assertEquals(100, rekening.saldo());
        assertEquals(1, rekening.personen().size());
    }
}