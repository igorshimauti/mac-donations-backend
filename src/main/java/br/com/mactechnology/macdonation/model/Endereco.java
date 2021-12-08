package br.com.mactechnology.macdonation.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 9, nullable = false)
    @Size(min = 9, max = 9)
    @NotBlank
    private String cep;

    @Column(length = 2, nullable = false)
    @Size(min = 2, max = 2)
    @NotBlank
    private String uf;

    @Column(length = 50, nullable = false)
    @Size(max = 50)
    @NotBlank
    private String cidade;

    @Column(length = 50, nullable = false)
    @Size(max = 50)
    @NotBlank
    private String bairro;

    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @NotBlank
    private String logradouro;

    @Column(length = 20, nullable = false)
    @Size(max = 20)
    @NotBlank
    private String numero;

    @Column(length = 50)
    @Size(max = 50)
    private String complemento;

    public Endereco() {
    }

    public Endereco(String cep, String uf, String cidade, String bairro, String logradouro, String numero, String complemento) {
        this.cep = cep;
        this.uf = uf;
        this.cidade = cidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return id.equals(endereco.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}