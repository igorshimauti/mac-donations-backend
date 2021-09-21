package br.com.mactechnology.macdonation.service;

import br.com.mactechnology.macdonation.exception.BusinessRulesException;
import br.com.mactechnology.macdonation.model.Doacao;
import br.com.mactechnology.macdonation.model.Familiar;
import br.com.mactechnology.macdonation.repository.DoacaoRepository;
import br.com.mactechnology.macdonation.repository.FamiliarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FamiliarService {

    @Autowired
    private FamiliarRepository familiarRepository;

    @Transactional
    public Familiar save(Familiar familiar) {
        return familiarRepository.save(familiar);
    }

    @Transactional(readOnly = true)
    public List<Familiar> findByDonatarioId(Long donatarioId) {
        return familiarRepository.findByDonatarioIdOrderByNomeAsc(donatarioId);
    }

    @Transactional(readOnly = true)
    public Familiar findById(Long familiarId) {
        return familiarRepository.findById(familiarId).orElseThrow(() -> new BusinessRulesException("Familiar não encontrado."));
    }

    @Transactional
    public void deleteById(Long familiarId) {
        familiarRepository.deleteById(familiarId);
    }

    public boolean existsById(Long familiarId) {
        return familiarRepository.existsById(familiarId);
    }
}