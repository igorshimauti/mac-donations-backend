package br.com.mactechnology.macdonation.repository;

import br.com.mactechnology.macdonation.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByAutorizado(Boolean autorizado);
}