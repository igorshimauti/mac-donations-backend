package br.com.mactechnology.macdonation.repository;

import br.com.mactechnology.macdonation.model.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, Long> {

    List<Doacao> findByDonatarioIdOrderByDataDesc(Long donatarioId);
}