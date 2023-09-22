package com.jb_cnsd.opdracht_3_4.cucumber;

import com.jb_cnsd.opdracht_3_4.data.models.RekeningStatus;
import com.jb_cnsd.opdracht_3_4.data.repository.PersoonRepository;
import com.jb_cnsd.opdracht_3_4.data.repository.RekeningRepository;
import com.jb_cnsd.opdracht_3_4.web.dto.requests.SaldoChangeRequest;
import com.jb_cnsd.opdracht_3_4.web.dto.responses.RekeningResponse;
import io.cucumber.java.en.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class SaldoStepDef {
    private final PersoonRepository persoonRepository;
    private final RekeningRepository rekeningRepository;
    private final TestRestTemplate restTemplate;

    public SaldoStepDef(PersoonRepository persoonRepository, RekeningRepository rekeningRepository, TestRestTemplate restTemplate) {
        this.persoonRepository = persoonRepository;
        this.rekeningRepository = rekeningRepository;
        this.restTemplate = restTemplate;
    }

    @LocalServerPort
    private int port;

    @Given("the user has an account")
    public void theUserHasAnAccount() {
        var persoon = persoonRepository.findById(1L).orElse(null);
        assertNotNull(persoon);
        assertEquals(1, persoon.getId());
    }

    @Given("the user has a rekening")
    public void theUserHasARekening() {
        var rekening = rekeningRepository.findById(1L).orElse(null);
        assertNotNull(rekening);
        assertEquals(1, rekening.getId());
        assertEquals(1, rekening.getPersonen().size());
    }

    @And("the rekening is not blocked")
    public void theRekeningIsNotBlocked() {
        var rekening = rekeningRepository.findById(1L).orElseThrow();
        assertNotEquals(RekeningStatus.GEBLOKKEERD, rekening.getStatus());
    }

    @And("the saldo is {float}")
    public void theSaldoIs(float initialSaldo) {
        var rekening = rekeningRepository.findById(1L).orElseThrow();
        rekening.setSaldo(initialSaldo);
        var savedEntity = rekeningRepository.save(rekening);
        assertEquals(initialSaldo, savedEntity.getSaldo());
    }

    @When("the user adds {float} to his saldo")
    public void theUserAddsToHisSaldo(float addedSaldo) throws URISyntaxException {
        var uri = new URI(String.format("http://localhost:%d/rekening/1/saldo", port));
        var requestBody = new SaldoChangeRequest(addedSaldo);
        restTemplate.exchange(new RequestEntity<>(requestBody, HttpMethod.POST, uri), RekeningResponse.class);
    }

    @When("the user removes {float} from his saldo")
    public void theUserRemovesFromHisSaldo(float removedSaldo) throws URISyntaxException {
        var uri = new URI(String.format("http://localhost:%d/rekening/1/saldo", port));
        var requestBody = new SaldoChangeRequest(removedSaldo);
        restTemplate.exchange(new RequestEntity<>(requestBody, HttpMethod.DELETE, uri), RekeningResponse.class);
    }

    @Then("the saldo is updated to {float}")
    public void theSaldoIsUpdatedTo(float expectedSaldo) {
        var rekening = rekeningRepository.findById(1L).orElseThrow();
        assertEquals(expectedSaldo, rekening.getSaldo());
    }
}
