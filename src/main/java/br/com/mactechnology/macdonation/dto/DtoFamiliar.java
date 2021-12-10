package br.com.mactechnology.macdonation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DtoFamiliar {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String nome;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private byte idade;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String nivelParentesco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte getIdade() {
        return idade;
    }

    public void setIdade(byte idade) {
        this.idade = idade;
    }

    public String getNivelParentesco() {
        return nivelParentesco;
    }

    public void setNivelParentesco(String nivelParentesco) {
        this.nivelParentesco = nivelParentesco;
    }
}