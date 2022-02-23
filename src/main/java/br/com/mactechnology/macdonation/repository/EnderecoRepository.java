package br.com.mactechnology.macdonation.repository;

import br.com.mactechnology.macdonation.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    boolean existsByCepAndUfAndCidadeAndBairroAndLogradouroAndNumeroAndComplemento(String cep, String uf, String cidade, String bairro, String logradouro, String numero, String complemento);
}