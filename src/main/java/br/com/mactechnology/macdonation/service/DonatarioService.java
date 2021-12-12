package br.com.mactechnology.macdonation.service;

import br.com.mactechnology.macdonation.common.MacTechnologyUtils;
import br.com.mactechnology.macdonation.exception.BusinessRulesException;
import br.com.mactechnology.macdonation.model.Donatario;
import br.com.mactechnology.macdonation.repository.DonatarioRepository;
import br.com.mactechnology.macdonation.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DonatarioService {

    @Autowired
    private DonatarioRepository donatarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public Donatario save(Donatario donatario) {
        if (!MacTechnologyUtils.isCpf(donatario.getCpf())) {
            throw new BusinessRulesException(donatario.getCpf() + " não é um CPF válido.");
        }

        boolean donatarioDuplicado = donatarioRepository.findByCpf(donatario.getCpf()).stream().anyMatch(donatarioExistente -> !donatarioExistente.equals(donatario));
        if (donatarioDuplicado) {
            throw new BusinessRulesException("Donatário com CPF '" + donatario.getCpf() + "' já foi cadastrado anteriormente.");
        }

        if (donatario.getCelular() != null) {
            donatarioDuplicado = donatarioRepository.findByCelular(donatario.getCelular()).stream().anyMatch(donatarioExistente -> !donatarioExistente.equals(donatario));
            if (donatarioDuplicado) {
                throw new BusinessRulesException("Donatário com celular '" + donatario.getCelular() + "' já foi cadastrado anteriormente.");
            }
        }

        if (donatario.getEndereco() != null && donatario.getEndereco().getId() != null && !enderecoRepository.existsById(donatario.getEndereco().getId())) {
            throw new BusinessRulesException("Endereço não encontrado.");
        }

        return donatarioRepository.save(donatario);
    }

    @Transactional(readOnly = true)
    public List<Donatario> findAll() {
        return donatarioRepository.findByOrderByNome();
    }

    @Transactional(readOnly = true)
    public Donatario findById(Long donatarioId) {
        return donatarioRepository.findById(donatarioId).orElseThrow(() -> new BusinessRulesException("Donatário não encontrado."));
    }

    @Transactional
    public void deleteById(Long donatarioId) {
        donatarioRepository.deleteById(donatarioId);
    }

    public boolean existsById(Long donatarioId) {
        return donatarioRepository.existsById(donatarioId);
    }
}