package br.com.mactechnology.macdonation.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.mactechnology.macdonation.model.Usuario;
import br.com.mactechnology.macdonation.exception.BusinessRulesException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value("${macdonations.jwt.expiracao}")
    private String expiracao;

    @Value("${macdonations.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication auth) {

        Date dataCriacao = new Date();
        Date dataExpiracao = new Date(dataCriacao.getTime() + Long.parseLong(expiracao));
        Usuario logado = (Usuario) auth.getPrincipal();

        if (!logado.getAutorizado()) {
            throw new BusinessRulesException("Usuário encontrado mas não possui acesso liberado. Contacte um administrador.");
        }

        return Jwts.builder().setIssuer("mac-donations")
                .setId(logado.getId().toString())
                .setSubject(logado.getNome())
                .setIssuer(logado.getAdmin().toString())
                .setIssuedAt(dataCriacao)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUsuarioId(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        Long usuarioId = Long.parseLong(claims.getId());
        return usuarioId;
    }
}