package br.com.mactechnology.macdonation.repository;

import br.com.mactechnology.macdonation.model.Donatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonatarioRepository extends JpaRepository<Donatario, Long> {

    Optional<Donatario> findByCpf(String cpf);
    Optional<Donatario> findByCelular(String celular);
    List<Donatario> findByOrderByNome();
}