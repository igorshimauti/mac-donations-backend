package br.com.mactechnology.macdonation.service;

import br.com.mactechnology.macdonation.exception.BusinessException;
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
        if (doacao.getId() != null && !doacaoRepository.existsByIdAndDonatarioId(doacao.getId(), doacao.getDonatario().getId())) {
            throw new BusinessException("Doação não encontrada para o donatário informado.");
        }

        return doacaoRepository.save(doacao);
    }

    @Transactional(readOnly = true)
    public List<Doacao> findByDonatarioId(Long donatarioId) {
        return doacaoRepository.findByDonatarioIdOrderByDataDesc(donatarioId);
    }

    @Transactional(readOnly = true)
    public Doacao findById(Long donatarioId, Long doacaoId) {
        if (!doacaoRepository.existsByIdAndDonatarioId(doacaoId, donatarioId)) {
            throw new BusinessException("Doação não encontrada para o donatário informado.");
        }

        return doacaoRepository.findById(doacaoId).orElseThrow(() -> new BusinessException("Doação não encontrada."));
    }

    @Transactional
    public void deleteById(Long donatarioId, Long doacaoId) {
        if (!doacaoRepository.existsByIdAndDonatarioId(doacaoId, donatarioId)) {
            throw new BusinessException("Doação não encontrada para o donatário informado.");
        }

        doacaoRepository.deleteById(doacaoId);
    }

    public boolean existsById(Long doacaoId) {
        return doacaoRepository.existsById(doacaoId);
    }
}