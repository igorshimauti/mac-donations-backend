package br.com.mactechnology.macdonation.dto;

public class DtoToken {

	private String tipo;
	private String token;

	public DtoToken(String tipo, String token) {
		this.tipo = tipo;
		this.token = token;
	}

	public String getTipo() {
		return tipo;
	}

	public String getToken() {
		return token;
	}
}