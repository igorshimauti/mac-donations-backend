package br.com.mactechnology.macdonation.dto;

import br.com.mactechnology.macdonation.model.Donatario;

import java.time.LocalDate;

public class DtoDoacao {

    private Long id;
    private LocalDate data;
    private String descricao;

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
}