package br.com.mactechnology.macdonation.dto;

import br.com.mactechnology.macdonation.model.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class DtoDonatario {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String nome;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String cpf;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private LocalDate dataNascimento;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String celular;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Endereco endereco;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}