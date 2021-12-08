package br.com.mactechnology.macdonation.service;

import br.com.mactechnology.macdonation.model.Donatario;
import br.com.mactechnology.macdonation.model.Endereco;
import br.com.mactechnology.macdonation.model.Familiar;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class FamiliarServiceTest {

    @Autowired
    private DonatarioService donatarioService;

    @Autowired
    private FamiliarService familiarService;
    private Familiar familiar;

    @BeforeAll
    public void initialize() {
        Endereco endereco = new Endereco(
                "04678-100",
                "SP",
                "São Paulo",
                "Jardim Campininha",
                "Rua João Franco Oliveira",
                "549",
                "BL 4 - APTO 84"
        );

        Donatario donatario = donatarioService.save(new Donatario(
                "Joaquim",
                "943.210.100-20",
                LocalDate.of(1993, 5, 26),
                "(11) 98765-5432",
                endereco,
                null,
                null
        ));

        this.familiar = new Familiar(
                "Maria",
                (byte) 7,
                "Filha",
                donatario
        );
    }

    @Test
    @Order(1)
    public void saveTest() {
        Familiar familiarSalvo = familiarService.save(this.familiar);
        assertNotNull(familiarSalvo);

        this.familiar.setId(familiarSalvo.getId());
        assertEquals(this.familiar, familiarSalvo);
    }

    @Test
    @Order(2)
    public void findByDonatarioIdTest() {
        List<Familiar> familiares = familiarService.findByDonatarioId(this.familiar.getDonatario().getId());
        assertNotNull(familiares);
    }

    @Test
    @Order(3)
    public void existsByIdTest() {
        boolean familiarEncontrado = familiarService.existsById(this.familiar.getId());
        assertTrue(familiarEncontrado);
    }

    @Test
    @Order(4)
    public void findByIdTest() {
        Familiar familiarEncontrado = familiarService.findById(this.familiar.getId());
        assertEquals(this.familiar, familiarEncontrado);
    }

    @Test
    @Order(5)
    public void deleteByIdTest() {
        familiarService.deleteById(this.familiar.getId());
        boolean familiarEncontrado = familiarService.existsById(this.familiar.getId());
        assertFalse(familiarEncontrado);
    }
}