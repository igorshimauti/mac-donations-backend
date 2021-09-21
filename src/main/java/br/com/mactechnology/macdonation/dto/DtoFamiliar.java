package br.com.mactechnology.macdonation.dto;

public class DtoFamiliar {

    private Long id;
    private String nome;
    private byte idade;
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