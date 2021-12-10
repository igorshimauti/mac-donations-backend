package br.com.mactechnology.macdonation.mapper;

import br.com.mactechnology.macdonation.dto.DtoDonatario;
import br.com.mactechnology.macdonation.model.Donatario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DonatarioMapper {

    @Autowired
    private ModelMapper modelMapper;

    public DtoDonatario toDto(Donatario donatario) {
        return modelMapper.map(donatario, DtoDonatario.class);
    }

    public List<DtoDonatario> toCollectionDto(List<Donatario> donatarios) {
        return donatarios.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Donatario toEntity(DtoDonatario dtoDonatario) {
        return modelMapper.map(dtoDonatario, Donatario.class);
    }
}