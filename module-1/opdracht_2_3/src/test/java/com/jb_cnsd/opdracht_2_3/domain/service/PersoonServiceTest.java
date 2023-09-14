package com.jb_cnsd.opdracht_2_3.domain.service;

import com.jb_cnsd.opdracht_2_3.data.models.Persoon;
import com.jb_cnsd.opdracht_2_3.data.repository.PersoonRepository;
import com.jb_cnsd.opdracht_2_3.domain.exceptions.AlreadyExistsException;
import com.jb_cnsd.opdracht_2_3.domain.exceptions.NotFoundException;
import com.jb_cnsd.opdracht_2_3.web.dto.requests.PersoonCreateRequest;
import com.jb_cnsd.opdracht_2_3.web.dto.requests.PersoonEditRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class PersoonServiceTest {
    @Mock
    PersoonRepository persoonRepository;
    @InjectMocks
    PersoonService persoonService;
    @Captor
    ArgumentCaptor<Persoon> persoonCaptor;

    @BeforeEach
    void setUp() {
        var p1 = new Persoon("123456789", "Bob");
        var p2 = new Persoon("123456780", "Chris");
        Mockito.lenient().when(persoonRepository.findAll()).thenReturn(List.of(p1, p2));
        Mockito.lenient().when(persoonRepository.findById(1L)).thenReturn(Optional.of(p1));
        Mockito.lenient().when(persoonRepository.findById(2L)).thenReturn(Optional.of(p2));
        Mockito.lenient().when(persoonRepository.findById(3L)).thenReturn(Optional.empty());
    }

    @Test
    void getAll() {
        // Act
        var personen = persoonService.getAll();

        // Assert
        assertEquals(2, personen.size());
    }

    @Test
    void get() {
        // Arrange
        var persoon = persoonRepository.findById(1L).orElseThrow();

        // Act
        var response = persoonService.get(1L);

        // Assert
        assertEquals(persoon, response);
    }

    @Test
    void getNotFound() {
        // Act & Assert
        assertThrows(NotFoundException.class, () -> persoonService.get(3L));
    }

    @Test
    void create() {
        // Arrange
        Mockito.when(persoonRepository.save(persoonCaptor.capture())).thenAnswer(a -> a.getArguments()[0]);

        // Act
        var result = persoonService.create(new PersoonCreateRequest("234567890", "Test"));

        // Assert
        assertEquals(persoonCaptor.getValue(), result);
        assertEquals("Test", result.getNaam());
        assertEquals("234567890", result.getBsn());
        assertEquals(0, result.getRekeningen().size());
    }

    @Test
    void createExistingIban() {
        // Arrange
        Mockito.when(persoonRepository.existsByBsn(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(AlreadyExistsException.class, () -> persoonService.create(new PersoonCreateRequest("123456789", "Test")));
    }

    @Test
    void edit() {
        // Arrange
        var persoon = persoonRepository.findById(1L).orElseThrow();
        Mockito.when(persoonRepository.save(persoonCaptor.capture())).thenReturn(persoon);

        // Act
        var result = persoonService.edit(1L, new PersoonEditRequest("Test"));

        // Assert
        assertEquals("Test", result.getNaam());
    }

    @Test
    void editNotFound() {
        // Act & Assert
        assertThrows(NotFoundException.class, () -> persoonService.edit(3L, new PersoonEditRequest("Test")));
    }

    @Test
    void remove() {
        // Arrange
        var persoon = persoonRepository.findById(1L).orElseThrow();
        Mockito.doNothing().when(persoonRepository).delete(persoonCaptor.capture());

        // Act
        persoonService.remove(1);

        // Assert
        assertEquals(persoon, persoonCaptor.getValue());
    }

    @Test
    void removeNotFound() {
        // Act & Assert
        assertThrows(NotFoundException.class, () -> persoonService.remove(3L));
    }
}