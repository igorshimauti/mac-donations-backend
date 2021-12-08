package br.com.mactechnology.macdonation.service;

import br.com.mactechnology.macdonation.exception.BusinessRulesException;
import br.com.mactechnology.macdonation.model.Usuario;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;
    private Usuario usuario;

    @BeforeAll
    public void initialize() {
        this.usuario = new Usuario(
                "Joana",
                "423.709.150-30",
                "joana@teste.com",
                "123456789"
        );
    }

    @Test
    @Order(1)
    public void saveTest() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Usuario usuarioSalvo = usuarioService.save(this.usuario);
        assertNotNull(usuarioSalvo);

        this.usuario.setId(usuarioSalvo.getId());
        assertEquals(this.usuario, usuarioSalvo);
    }

    @Test
    @Order(2)
    public void findByIdTest() {
        Usuario usuarioEncontrado = usuarioService.findById(this.usuario.getId());
        assertEquals(this.usuario, usuarioEncontrado);
    }

    @Test
    @Order(3)
    public void deleteByIdTest() {
        usuarioService.delete(this.usuario.getId());
        BusinessRulesException exception = assertThrows(
                BusinessRulesException.class,
                () -> usuarioService.findById(this.usuario.getId()),
                "Esperado erro ao buscar por usuário excluído."
        );

        assertTrue(exception.getMessage().equals("Usuário não encontrado."));
    }
}