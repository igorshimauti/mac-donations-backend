package br.com.mactechnology.macdonation.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "donatario")
public class Donatario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "donatario_id_seq")
    private Long id;

    @Column(length = 150)
    @Size(max = 150)
    @NotBlank
    private String nome;

    @Column(length = 14, nullable = false, unique = true)
    @Size(min = 14, max = 14)
    @NotBlank
    private String cpf;

    @NotNull
    private LocalDate dataNascimento;

    @Column(length = 15)
    @Size(max = 15)
    private String celular;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "donatario")
    private List<Familiar> familiares;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "donatario")
    private List<Doacao> doacoes;

    public Donatario() {
    }

    public Donatario(String nome, String cpf, LocalDate dataNascimento, String celular, Endereco endereco, List<Familiar> familiares, List<Doacao> doacoes) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.celular = celular;
        this.endereco = endereco;
        this.familiares = familiares;
        this.doacoes = doacoes;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donatario donatario = (Donatario) o;
        return id.equals(donatario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}