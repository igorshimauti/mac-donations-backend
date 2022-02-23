package br.com.mactechnology.macdonation.repository;

import br.com.mactechnology.macdonation.model.Familiar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamiliarRepository extends JpaRepository<Familiar, Long> {

    List<Familiar> findByDonatarioIdOrderByNomeAsc(Long donatarioId);
    boolean existsByIdAndDonatarioId(Long id, Long donatarioId);
}