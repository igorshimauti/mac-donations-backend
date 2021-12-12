package br.com.mactechnology.macdonation.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "doacao")
public class Doacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "doacao_id_seq")
    private Long id;

    @Column
    @NotNull
    private LocalDate data;

    @Column(length = 255)
    @Size(max = 255)
    @NotBlank
    private String descricao;

    @ManyToOne()
    @JoinColumn(name = "donatario_id", nullable = false)
    private Donatario donatario;

    public Doacao() {
    }

    public Doacao(LocalDate data, String descricao, Donatario donatario) {
        this.data = data;
        this.descricao = descricao;
        this.donatario = donatario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        Doacao doacao = (Doacao) o;
        return id.equals(doacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}