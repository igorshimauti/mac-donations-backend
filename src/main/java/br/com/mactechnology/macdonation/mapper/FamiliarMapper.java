package br.com.mactechnology.macdonation.mapper;

import br.com.mactechnology.macdonation.dto.DtoFamiliar;
import br.com.mactechnology.macdonation.model.Familiar;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FamiliarMapper {

    @Autowired
    private ModelMapper modelMapper;

    public DtoFamiliar toDto(Familiar familiar) {
        return modelMapper.map(familiar, DtoFamiliar.class);
    }

    public List<DtoFamiliar> toCollectionDto(List<Familiar> familiares) {
        return familiares.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Familiar toEntity(DtoFamiliar dtoFamiliar) {
        return modelMapper.map(dtoFamiliar, Familiar.class);
    }
}