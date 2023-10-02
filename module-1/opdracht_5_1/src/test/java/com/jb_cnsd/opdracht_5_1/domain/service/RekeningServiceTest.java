package com.jb_cnsd.opdracht_5_1.domain.service;

import com.jb_cnsd.opdracht_5_1.data.models.Iban;
import com.jb_cnsd.opdracht_5_1.data.models.Persoon;
import com.jb_cnsd.opdracht_5_1.data.models.Rekening;
import com.jb_cnsd.opdracht_5_1.data.models.RekeningStatus;
import com.jb_cnsd.opdracht_5_1.data.repository.PersoonRepository;
import com.jb_cnsd.opdracht_5_1.data.repository.RekeningRepository;
import com.jb_cnsd.opdracht_5_1.domain.exceptions.NegativeSaldoValueException;
import com.jb_cnsd.opdracht_5_1.domain.exceptions.NotFoundException;
import com.jb_cnsd.opdracht_5_1.domain.exceptions.RekeningException;
import com.jb_cnsd.opdracht_5_1.web.dto.requests.RekeningEditRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RekeningServiceTest {
    @Mock
    RekeningRepository rekeningRepository;
    @Mock
    PersoonRepository persoonRepository;
    @InjectMocks
    RekeningService rekeningService;
    @Captor
    ArgumentCaptor<Rekening> rekeningCaptor;

    @BeforeEach
    void setUp() {
        var p1 = new Persoon("123456789", "Bob");
        var p2 = new Persoon("123456780", "Chris");
        Mockito.lenient().when(persoonRepository.findAll()).thenReturn(List.of(p1, p2));
        Mockito.lenient().when(persoonRepository.findById(1L)).thenReturn(Optional.of(p1));
        Mockito.lenient().when(persoonRepository.findById(2L)).thenReturn(Optional.of(p2));
        Mockito.lenient().when(persoonRepository.findById(3L)).thenReturn(Optional.empty());

        var rekening = new Rekening(new Iban("NL99CNSD0897531194"), p1);
        Mockito.lenient().when(rekeningRepository.findAll()).thenReturn(List.of(rekening));
        Mockito.lenient().when(rekeningRepository.findById(1L)).thenReturn(Optional.of(rekening));
        Mockito.lenient().when(rekeningRepository.findById(2L)).thenReturn(Optional.empty());
    }

    @Test
    void getAll() {
        // Act
        var rekeningen = rekeningService.getAll();

        // Assert
        assertEquals(1, rekeningen.size());
    }

    @Test
    void get() {
        // Arrange
        var rekening = rekeningRepository.findById(1L).orElseThrow();

        // Act
        var result = rekeningService.get(1);

        // Assert
        assertNotNull(result);
        assertEquals(rekening, result);
        assertEquals(18, result.getIban().toString().length());
    }

    @Test
    void getNotFound() {
        // Act & Assert
        assertThrows(NotFoundException.class, () -> rekeningService.get(3L));
    }

    @Test
    void create() {
        // Arrange
        Mockito.lenient().when(rekeningRepository.save(rekeningCaptor.capture())).thenAnswer(a -> a.getArguments()[0]);

        // Act
        var result = rekeningService.create(1);

        // Assert
        assertEquals(rekeningCaptor.getValue(), result);
        assertNotNull(result.getIban());
        assertEquals(18, result.getIban().toString().length());
        assertEquals(0, result.getSaldo());
        assertEquals(1, result.getPersonen().size());
        assertEquals(RekeningStatus.NORMAAL, result.getStatus());
    }

    @Test
    void createPersoonNotFound() {
        // Act & Assert
        assertThrows(NotFoundException.class, () -> rekeningService.create(3L));
    }

    @Test
    void edit() {
        // Arrange
        var rekening = rekeningRepository.findById(1L).orElseThrow();
        Mockito.lenient().when(rekeningRepository.save(rekeningCaptor.capture())).thenReturn(rekening);

        // Act
        var result = rekeningService.edit(1, new RekeningEditRequest(RekeningStatus.GEBLOKKEERD));

        // Assert
        assertEquals(rekeningCaptor.getValue(), result);
        assertEquals(RekeningStatus.GEBLOKKEERD, result.getStatus());
    }

    @Test
    void editNotFound() {
        // Arrange
        var request = new RekeningEditRequest(RekeningStatus.GEBLOKKEERD);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> rekeningService.edit(3, request));
    }

    @Test
    void remove() {
        // Arrange
        var rekening = rekeningRepository.findById(1L).orElseThrow();
        Mockito.doNothing().when(rekeningRepository).delete(rekeningCaptor.capture());

        // Act
        rekeningService.remove(1);

        // Assert
        assertEquals(rekening, rekeningCaptor.getValue());
    }

    @Test
    void addSaldo() {
        // Arrange
        var rekening = rekeningRepository.findById(1L).orElseThrow();
        Mockito.lenient().when(rekeningRepository.save(rekeningCaptor.capture())).thenReturn(rekening);

        // Act
        var result = rekeningService.addSaldo(1L, 100);

        // Assert
        assertEquals(rekeningCaptor.getValue(), result);
        assertEquals(100, result.getSaldo());
    }

    @Test
    void addSaldoNegative() {
        // Act & Assert
        assertThrows(NegativeSaldoValueException.class, () -> rekeningService.addSaldo(1L, -100));
    }

    @Test
    void addSaldoRekeningBlocked() {
        // Arrange
        var rekening = rekeningRepository.findById(1L).orElseThrow();
        rekening.setStatus(RekeningStatus.GEBLOKKEERD);

        // Act & Assert
        assertThrows(RekeningException.class, () -> rekeningService.addSaldo(1L, 100));
    }

    @Test
    void removeSaldo() {
        // Arrange
        var rekening = rekeningRepository.findById(1L).orElseThrow();
        Mockito.lenient().when(rekeningRepository.save(rekeningCaptor.capture())).thenReturn(rekening);

        // Act
        var result = rekeningService.removeSaldo(1L, 100);

        // Assert
        assertEquals(rekeningCaptor.getValue(), result);
        assertEquals(-100, result.getSaldo());
    }

    @Test
    void removeSaldoNegative() {
        // Act & Assert
        assertThrows(NegativeSaldoValueException.class, () -> rekeningService.removeSaldo(1L, -100));
    }

    @Test
    void removeSaldoRekeningBlocked() {
        // Arrange
        var rekening = rekeningRepository.findById(1L).orElseThrow();
        rekening.setStatus(RekeningStatus.GEBLOKKEERD);

        // Act & Assert
        assertThrows(RekeningException.class, () -> rekeningService.removeSaldo(1L, 100));
    }

    @Test
    void addPersoon() {
        // Arrange
        var rekening = rekeningRepository.findById(1L).orElseThrow();
        Mockito.lenient().when(rekeningRepository.save(rekeningCaptor.capture())).thenReturn(rekening);

        // Act
        var result = rekeningService.addPersoon(1, 2);

        // Assert
        assertEquals(rekeningCaptor.getValue(), result);
        assertEquals(2, result.getPersonen().size());
    }

    @Test
    void addPersoonNotFound() {
        // Act & Assert
        assertThrows(NotFoundException.class, () -> rekeningService.addPersoon(2, 1));
        assertThrows(NotFoundException.class, () -> rekeningService.addPersoon(1, 3));
    }

    @Test
    void removePersoon() {
        // Arrange
        var r1 = rekeningRepository.findById(1L).orElseThrow();
        var p1 = persoonRepository.findById(1L).orElseThrow();
        var p2 = persoonRepository.findById(2L).orElseThrow();
        r1.getPersonen().add(p2);
        Mockito.lenient().when(rekeningRepository.save(rekeningCaptor.capture())).thenReturn(r1);

        // Act
        var result = rekeningService.removePersoon(1, 2);

        // Assert
        assertEquals(rekeningCaptor.getValue(), result);
        assertEquals(1, result.getPersonen().size());
        assertEquals(p1, result.getPersonen().stream().findFirst().orElseThrow());
    }

    @Test
    void removePersoonNotFound() {
        // Act & Assert
        assertThrows(NotFoundException.class, () -> rekeningService.removePersoon(2, 1));
        assertThrows(NotFoundException.class, () -> rekeningService.removePersoon(1, 3));
    }

    @Test
    void removeLastPersoon() {
        // Act & Assert
        assertThrows(RekeningException.class, () -> rekeningService.removePersoon(1, 1));
    }
}