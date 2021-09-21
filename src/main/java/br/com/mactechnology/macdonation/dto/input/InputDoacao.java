package br.com.mactechnology.macdonation.dto.input;

import br.com.mactechnology.macdonation.model.Donatario;

import java.time.LocalDate;

public class InputDoacao {

    private LocalDate data;
    private String descricao;

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