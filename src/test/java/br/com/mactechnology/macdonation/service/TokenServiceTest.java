package br.com.mactechnology.macdonation.service;

import br.com.mactechnology.macdonation.dto.input.InputLogin;
import br.com.mactechnology.macdonation.exception.BusinessRulesException;
import br.com.mactechnology.macdonation.model.Usuario;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Usuario usuario;
    private InputLogin login;
    private String token;

    @BeforeAll
    public void initialize() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        this.usuario = usuarioService.save(new Usuario(
                "Fabiana",
                "964.359.020-89",
                "fabiana@teste.com",
                "12345678910"
        ));

        this.usuario.setAutorizado(true);
        this.usuario = usuarioService.save(this.usuario);
        this.login = new InputLogin(this.usuario.getEmail(), this.usuario.getSenha());
    }

    @Test
    @Order(1)
    public void gerarTokenTest() {
        UsernamePasswordAuthenticationToken dadosLogin = this.login.converte();
        Authentication authentication = authenticationManager.authenticate(dadosLogin);
        this.token = tokenService.gerarToken(authentication);
        assertNotNull(token);
        assertFalse(token.equals(""));

        boolean tokenValido = tokenService.isTokenValid(token);
        assertTrue(tokenValido);
    }

    @Test
    @Order(2)
    public void getUsuarioIdTest() {
        Long usuarioId = tokenService.getUsuarioId(this.token);
        assertEquals(this.usuario.getId(), usuarioId);
    }

    @Test
    @Order(3)
    public void gerarTokenExceptionTest() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.usuario.setAutorizado(false);
        this.usuario = usuarioService.save(this.usuario);

        UsernamePasswordAuthenticationToken dadosLogin = this.login.converte();
        Authentication authentication = authenticationManager.authenticate(dadosLogin);
        BusinessRulesException exception = assertThrows(
                BusinessRulesException.class,
                () -> tokenService.gerarToken(authentication),
                "Esperado erro de por acesso bloqueado."
        );

        assertTrue(exception.getMessage().equals("Usuário encontrado mas não possui acesso liberado. Contacte um administrador."));
    }

    @Test
    @Order(4)
    public void authenticateExceptionTest() {
        this.login.setSenha("123456");
        UsernamePasswordAuthenticationToken dadosLogin = this.login.converte();
        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> authenticationManager.authenticate(dadosLogin),
                "Esperado erro de credenciais incorretas."
        );

        assertTrue(exception.getMessage().equals("Bad credentials"));
    }
}