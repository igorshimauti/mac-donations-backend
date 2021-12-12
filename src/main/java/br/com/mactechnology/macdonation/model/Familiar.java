package br.com.mactechnology.macdonation.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "familiar")
public class Familiar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "familiar_id_seq")
    private Long id;

    @Column(length = 100)
    @Size(max = 100)
    @NotBlank
    private String nome;

    @NotNull
    private byte idade;

    @Column(length = 20)
    @Size(max = 20)
    @NotBlank
    private String nivelParentesco;

    @ManyToOne()
    @JoinColumn(name = "donatario_id", nullable = false)
    private Donatario donatario;

    public Familiar() {
    }

    public Familiar(String nome, byte idade, String nivelParentesco, Donatario donatario) {
        this.nome = nome;
        this.idade = idade;
        this.nivelParentesco = nivelParentesco;
        this.donatario = donatario;
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

    public Donatario getDonatario() {
        return donatario;
    }

    public void setDonatario(Donatario donatario) {
        this.donatario = donatario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Familiar familiar = (Familiar) o;
        return id.equals(familiar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}