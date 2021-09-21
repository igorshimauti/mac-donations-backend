package br.com.mactechnology.macdonation.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mactechnology.macdonation.model.Usuario;
import br.com.mactechnology.macdonation.repository.UsuarioRepository;
import br.com.mactechnology.macdonation.exception.BusinessRulesException;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (usuarioRepository.existsByEmail(usuario.getEmail()) && usuario.getId() == null) {
            throw new BusinessRulesException("Usuário com o login '" + usuario.getEmail() + "' já foi cadastrado anteriormente.");
        }

        if (usuarioRepository.existsByCpf(usuario.getCpf()) && usuario.getId() == null) {
            throw new BusinessRulesException("Usuário com o CPF '" + usuario.getCpf() + "' já foi cadastrado anteriormente.");
        }

        if (usuario.getId() == null) {
            usuario.setAutorizado(false);
            usuario.setAdmin(false);

            String hash = encriptarSenha(usuario.getSenha());
            usuario.setSenha(hash);
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void excluir(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }

    @Transactional(readOnly = true)
    public Usuario buscar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new BusinessRulesException("Usuário não encontrado."));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username).orElseThrow(() -> new BusinessRulesException("Usuário não encontrado."));
    }

    public String encriptarSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algoritmo.digest(senha.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();

        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }

        return hexString.toString();
    }
}