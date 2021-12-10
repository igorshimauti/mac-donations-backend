package br.com.mactechnology.macdonation.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mactechnology.macdonation.dto.DtoUsuario;
import br.com.mactechnology.macdonation.model.Usuario;

@Component
public class UsuarioMapper {

    @Autowired
    private ModelMapper modelMapper;

    public DtoUsuario toDto(Usuario usuario) {
        return modelMapper.map(usuario, DtoUsuario.class);
    }

    public List<DtoUsuario> toCollectionDto(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Usuario toEntity(DtoUsuario dtoUsuario) {
        return modelMapper.map(dtoUsuario, Usuario.class);
    }
}