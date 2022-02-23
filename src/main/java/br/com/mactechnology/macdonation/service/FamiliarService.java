package br.com.mactechnology.macdonation.service;

import br.com.mactechnology.macdonation.exception.BusinessException;
import br.com.mactechnology.macdonation.model.Familiar;
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
        if (familiar.getId() != null && !familiarRepository.existsByIdAndDonatarioId(familiar.getId(), familiar.getDonatario().getId())) {
            throw new BusinessException("Familiar não encontrado para o donatário informado.");
        }

        return familiarRepository.save(familiar);
    }

    @Transactional(readOnly = true)
    public List<Familiar> findByDonatarioId(Long donatarioId) {
        return familiarRepository.findByDonatarioIdOrderByNomeAsc(donatarioId);
    }

    @Transactional(readOnly = true)
    public Familiar findById(Long donatarioId, Long familiarId) {
        if (!familiarRepository.existsByIdAndDonatarioId(familiarId, donatarioId)) {
            throw new BusinessException("Familiar não encontrado para o donatário informado.");
        }

        return familiarRepository.findById(familiarId).orElseThrow(() -> new BusinessException("Familiar não encontrado."));
    }

    @Transactional
    public void deleteById(Long donatarioId, Long familiarId) {
        if (!familiarRepository.existsByIdAndDonatarioId(familiarId, donatarioId)) {
            throw new BusinessException("Familiar não encontrado para o donatário informado.");
        }

        familiarRepository.deleteById(familiarId);
    }

    public boolean existsById(Long familiarId) {
        return familiarRepository.existsById(familiarId);
    }
}