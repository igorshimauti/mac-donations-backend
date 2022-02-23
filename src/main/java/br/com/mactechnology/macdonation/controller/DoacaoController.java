package br.com.mactechnology.macdonation.controller;

import br.com.mactechnology.macdonation.dto.DtoDoacao;
import br.com.mactechnology.macdonation.exception.BusinessException;
import br.com.mactechnology.macdonation.mapper.DoacaoMapper;
import br.com.mactechnology.macdonation.model.Doacao;
import br.com.mactechnology.macdonation.service.DoacaoService;
import br.com.mactechnology.macdonation.service.DonatarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "donatario/{donatarioId}/doacao")
public class DoacaoController {

    @Autowired
    private DonatarioService donatarioService;

    @Autowired
    private DoacaoService doacaoService;

    @Autowired
    private DoacaoMapper doacaoMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDoacao(@PathVariable Long donatarioId, @RequestBody DtoDoacao dtoDoacao) {
        if (!donatarioService.existsById(donatarioId)) {
            return ResponseEntity.notFound().build();
        }

        try {
            Doacao doacao = doacaoMapper.toEntity(dtoDoacao);
            doacao.setDonatario(donatarioService.findById(donatarioId));
            DtoDoacao dtoDoacaoSalva = doacaoMapper.toDto(doacaoService.save(doacao));
            URI location = URI.create(String.format("donatario/%s/doacao/%s", donatarioId, dtoDoacaoSalva.getId()));
            return ResponseEntity.created(location).body(dtoDoacaoSalva);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DtoDoacao>> readDoacoes(@PathVariable Long donatarioId) {
        if (!donatarioService.existsById(donatarioId)) {
            return ResponseEntity.notFound().build();
        }

        List<Doacao> doacoes = doacaoService.findByDonatarioId(donatarioId);
        return ResponseEntity.ok(doacaoMapper.toCollectionDto(doacoes));
    }

    @GetMapping(value = "/{doacaoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readDoacaoById(@PathVariable Long donatarioId, @PathVariable Long doacaoId) {
        try {
            Doacao doacao = doacaoService.findById(donatarioId, doacaoId);
            return ResponseEntity.ok(doacaoMapper.toDto(doacao));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{doacaoId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDoacao(@PathVariable Long donatarioId, @PathVariable Long doacaoId, @RequestBody DtoDoacao dtoDoacao) {
        if (!donatarioService.existsById(donatarioId) || !doacaoService.existsById(doacaoId)) {
            return ResponseEntity.notFound().build();
        }

        try {
            Doacao doacao = doacaoMapper.toEntity(dtoDoacao);
            doacao.setDonatario(donatarioService.findById(donatarioId));
            doacao.setId(doacaoId);

            Doacao doacaoSalva = doacaoService.save(doacao);
            return ResponseEntity.ok(doacaoMapper.toDto(doacaoSalva));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{doacaoId}")
    public ResponseEntity<?> deleteDoacao(@PathVariable Long donatarioId, @PathVariable Long doacaoId) {
        if (!doacaoService.existsById(doacaoId)) {
            return ResponseEntity.notFound().build();
        }

        try {
            doacaoService.deleteById(donatarioId, doacaoId);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}