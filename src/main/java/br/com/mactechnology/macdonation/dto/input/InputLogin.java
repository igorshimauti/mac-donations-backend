package br.com.mactechnology.macdonation.dto.input;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class InputLogin {

	private String email;
	private String senha;

	public InputLogin(String email, String senha) {
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