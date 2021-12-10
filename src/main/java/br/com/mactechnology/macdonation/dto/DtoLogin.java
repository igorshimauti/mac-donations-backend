package br.com.mactechnology.macdonation.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class DtoLogin {

	private String email;
	private String senha;

	public DtoLogin(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UsernamePasswordAuthenticationToken converte() {
		return new UsernamePasswordAuthenticationToken(email, senha);
	}
}