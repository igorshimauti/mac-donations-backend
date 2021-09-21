package br.com.mactechnology.macdonation.controller;

import br.com.mactechnology.macdonation.dto.DtoDoacao;
import br.com.mactechnology.macdonation.dto.input.InputDoacao;
import br.com.mactechnology.macdonation.mapper.DoacaoMapper;
import br.com.mactechnology.macdonation.model.Doacao;
import br.com.mactechnology.macdonation.service.DoacaoService;
import br.com.mactechnology.macdonation.service.DonatarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "donatario/{donatarioId}")
public class DoacaoController {

    @Autowired
    private DonatarioService donatarioService;

    @Autowired
    private DoacaoService doacaoService;

    @Autowired
    private DoacaoMapper doacaoMapper;

    @PostMapping(value = "/doacao", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DtoDoacao> createDoacao(@PathVariable Long donatarioId, @RequestBody InputDoacao inputDoacao) {
        if (!donatarioService.existsById(donatarioId)) {
            return ResponseEntity.notFound().build();
        }

        Doacao doacao = doacaoMapper.toEntity(inputDoacao);
        doacao.setDonatario(donatarioService.findById(donatarioId));

        Doacao doacaoSalva = doacaoService.save(doacao);
        return ResponseEntity.ok(doacaoMapper.toDto(doacaoSalva));
    }

    @GetMapping(value = "/doacao", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DtoDoacao>> readDoacoes(@PathVariable Long donatarioId) {
        if (!donatarioService.existsById(donatarioId)) {
            return ResponseEntity.notFound().build();
        }

        List<Doacao> doacoes = doacaoService.findByDonatarioId(donatarioId);
        return ResponseEntity.ok(doacaoMapper.toCollectionDto(doacoes));
    }

    @GetMapping(value = "/doacao/{doacaoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoDoacao> readDoacaoById(@PathVariable Long doacaoId) {
        Doacao doacao = doacaoService.findById(doacaoId);
        return ResponseEntity.ok(doacaoMapper.toDto(doacao));
    }

    @PutMapping(value = "/doacao/{doacaoId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoDoacao> updateDoacao(@PathVariable Long donatarioId, @PathVariable Long doacaoId, @RequestBody InputDoacao inputDoacao) {
        if (!donatarioService.existsById(donatarioId) || !doacaoService.existsById(doacaoId)) {
            return ResponseEntity.notFound().build();
        }

        Doacao doacao = doacaoMapper.toEntity(inputDoacao);
        doacao.setDonatario(donatarioService.findById(donatarioId));
        doacao.setId(doacaoId);

        Doacao doacaoSalva = doacaoService.save(doacao);
        return ResponseEntity.ok(doacaoMapper.toDto(doacaoSalva));
    }

    @DeleteMapping(value = "/doacao/{doacaoId}")
    public ResponseEntity<Void> deleteDoacao(@PathVariable Long doacaoId) {
        if (!doacaoService.existsById(doacaoId)) {
            return ResponseEntity.notFound().build();
        }

        doacaoService.deleteById(doacaoId);
        return ResponseEntity.noContent().build();
    }
}