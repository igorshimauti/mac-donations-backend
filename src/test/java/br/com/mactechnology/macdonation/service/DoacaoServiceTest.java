package br.com.mactechnology.macdonation.service;

import br.com.mactechnology.macdonation.model.Doacao;
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
public class DoacaoServiceTest {

    @Autowired
    private DonatarioService donatarioService;

    @Autowired
    private DoacaoService doacaoService;
    private Doacao doacao;

    @BeforeAll
    public void initialize() {
        Endereco endereco = new Endereco(
                "05854-060",
                "SP",
                "São Paulo",
                "Parque Maria Helena",
                "Rua Cabo Estácio da Conceição",
                "1285",
                "BL 8 - APTO 112"
        );

        Donatario donatario = donatarioService.save(new Donatario(
                "Joaquina",
                "526.993.790-50",
                LocalDate.of(1993, 5, 26),
                "(11) 99999-9999",
                endereco,
                null,
                null
        ));

        this.doacao = new Doacao(
                LocalDate.now(),
                "Cesta básica",
                donatario
        );
    }

    @Test
    @Order(1)
    public void saveTest() {
        Doacao doacaoSalva = doacaoService.save(this.doacao);
        assertNotNull(doacaoSalva);

        this.doacao.setId(doacaoSalva.getId());
        assertEquals(this.doacao, doacaoSalva);
    }

    @Test
    @Order(2)
    public void findByDonatarioIdTest() {
        List<Doacao> doacoes = doacaoService.findByDonatarioId(this.doacao.getDonatario().getId());
        assertNotNull(doacoes);
    }

    @Test
    @Order(3)
    public void existsByIdTest() {
        boolean doacaoEncontrada = doacaoService.existsById(this.doacao.getId());
        assertTrue(doacaoEncontrada);
    }

    @Test
    @Order(4)
    public void findByIdTest() {
        Doacao doacaoEncontrada = doacaoService.findById(this.doacao.getId());
        assertEquals(this.doacao, doacaoEncontrada);
    }

    @Test
    @Order(5)
    public void deleteByIdTest() {
        doacaoService.deleteById(this.doacao.getId());
        boolean doacaoEncontrada = doacaoService.existsById(this.doacao.getId());
        assertFalse(doacaoEncontrada);
    }
}