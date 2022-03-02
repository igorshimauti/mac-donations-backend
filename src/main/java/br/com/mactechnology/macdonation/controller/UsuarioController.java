package br.com.mactechnology.macdonation.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.validation.Valid;

import br.com.mactechnology.macdonation.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mactechnology.macdonation.dto.DtoToken;
import br.com.mactechnology.macdonation.dto.DtoUsuario;
import br.com.mactechnology.macdonation.dto.DtoLogin;
import br.com.mactechnology.macdonation.mapper.UsuarioMapper;
import br.com.mactechnology.macdonation.model.Usuario;
import br.com.mactechnology.macdonation.service.TokenService;
import br.com.mactechnology.macdonation.service.UsuarioService;

//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin
@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody DtoUsuario dtoUsuario) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            Usuario usuario = usuarioService.save(usuarioMapper.toEntity(dtoUsuario));
            URI location = URI.create(String.format("usuario/%s", usuario.getId()));
            return ResponseEntity.created(location).body(usuarioMapper.toDto(usuario));
        }  catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DtoUsuario>> read() {
        return ResponseEntity.ok(usuarioMapper.toCollectionDto(usuarioService.findAll()));
    }

    @GetMapping(value = "/bloqueados", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DtoUsuario>> readBlocked() {
        return ResponseEntity.ok(usuarioMapper.toCollectionDto(usuarioService.findByAutorizado(false)));
    }

    @GetMapping(value = "/autorizados", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DtoUsuario>> readAuthorized() {
        return ResponseEntity.ok(usuarioMapper.toCollectionDto(usuarioService.findByAutorizado(true)));
    }

    @GetMapping(value = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readById(@PathVariable Long usuarioId) {
        try {
            Usuario usuario = usuarioService.findById(usuarioId);
            return ResponseEntity.ok(usuarioMapper.toDto(usuario));
        }  catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{usuarioId}/autorizar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authorize(@PathVariable Long usuarioId) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            Usuario usuario = usuarioService.findById(usuarioId);
            usuario.setAutorizado(true);
            return ResponseEntity.ok(usuarioMapper.toDto(usuarioService.save(usuario)));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{usuarioId}/setAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setAdmin(@PathVariable Long usuarioId) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            Usuario usuario = usuarioService.findById(usuarioId);
            usuario.setAdmin(true);
            return ResponseEntity.ok(usuarioMapper.toDto(usuarioService.save(usuario)));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{usuarioId}")
    public ResponseEntity<Void> delete(@PathVariable Long usuarioId) {
        if (!usuarioService.existsById(usuarioId)) {
            return ResponseEntity.notFound().build();
        }

        usuarioService.delete(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logar")
    public ResponseEntity<?> logar(@RequestBody DtoLogin input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String hash = usuarioService.encriptPassword(input.getSenha());
        input.setSenha(hash);

        try {
            UsernamePasswordAuthenticationToken dadosLogin = input.converte();
            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new DtoToken("Bearer", token));
        } catch (AuthenticationException | BusinessException e) {
            if (e.getMessage().equals("Bad credentials")) {
                return ResponseEntity.badRequest().body("Senha incorreta.");
            }

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}