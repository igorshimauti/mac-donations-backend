package br.com.mactechnology.macdonation.service;

import br.com.mactechnology.macdonation.exception.BusinessRulesException;
import br.com.mactechnology.macdonation.model.Doacao;
import br.com.mactechnology.macdonation.repository.DoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoacaoService {

    @Autowired
    private DoacaoRepository doacaoRepository;

    @Transactional
    public Doacao save(Doacao doacao) {
        return doacaoRepository.save(doacao);
    }

    @Transactional(readOnly = true)
    public List<Doacao> findByDonatarioId(Long donatarioId) {
        return doacaoRepository.findByDonatarioIdOrderByDataDesc(donatarioId);
    }

    @Transactional(readOnly = true)
    public Doacao findById(Long doacaoId) {
        return doacaoRepository.findById(doacaoId).orElseThrow(() -> new BusinessRulesException("Doação não encontrado."));
    }

    @Transactional
    public void deleteById(Long doacaoId) {
        doacaoRepository.deleteById(doacaoId);
    }

    public boolean existsById(Long doacaoId) {
        return doacaoRepository.existsById(doacaoId);
    }
}