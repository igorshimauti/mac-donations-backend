package br.com.mactechnology.macdonation.mapper;

import br.com.mactechnology.macdonation.dto.DtoDoacao;
import br.com.mactechnology.macdonation.dto.input.InputDoacao;
import br.com.mactechnology.macdonation.model.Doacao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoacaoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public DtoDoacao toDto(Doacao doacao) {
        return modelMapper.map(doacao, DtoDoacao.class);
    }

    public List<DtoDoacao> toCollectionDto(List<Doacao> doacoes) {
        return doacoes.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Doacao toEntity(InputDoacao input) {
        return modelMapper.map(input, Doacao.class);
    }
}