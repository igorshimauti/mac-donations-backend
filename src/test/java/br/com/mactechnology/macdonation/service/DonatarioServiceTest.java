package br.com.mactechnology.macdonation.service;

import br.com.mactechnology.macdonation.model.Donatario;
import br.com.mactechnology.macdonation.model.Endereco;
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
public class DonatarioServiceTest {

    @Autowired
    private DonatarioService donatarioService;
    private Donatario donatario;

    @BeforeAll
    public void initialize() {
        Endereco endereco = new Endereco(
                "04457-000",
                "SP",
                "São Paulo",
                "Jardim Palmares",
                "Rua Orestes Barbosa",
                "4541",
                ""
        );

        this.donatario = new Donatario(
                "João",
                "072.099.070-09",
                LocalDate.of(1993, 5, 26),
                "(11) 92345-5678",
                endereco,
                null,
                null
        );
    }

    @Test
    @Order(1)
    public void saveTest() {
        Donatario donatarioSalvo = donatarioService.save(this.donatario);
        assertNotNull(donatarioSalvo);

        this.donatario.setId(donatario.getId());
        assertEquals(this.donatario, donatarioSalvo);
    }

    @Test
    @Order(2)
    public void findAllTest() {
        List<Donatario> donatarios = donatarioService.findAll();
        assertNotNull(donatarios);
    }

    @Test
    @Order(3)
    public void existsByIdTest() {
        boolean donatarioEncontrado = donatarioService.existsById(this.donatario.getId());
        assertTrue(donatarioEncontrado);
    }

    @Test
    @Order(4)
    public void findByIdTest() {
        Donatario donatarioEncontrado = donatarioService.findById(this.donatario.getId());
        assertEquals(this.donatario, donatarioEncontrado);
    }

    @Test
    @Order(5)
    public void deleteByIdTest() {
        donatarioService.deleteById(this.donatario.getId());
        boolean donatarioEncontrado = donatarioService.existsById(this.donatario.getId());
        assertFalse(donatarioEncontrado);
    }
}