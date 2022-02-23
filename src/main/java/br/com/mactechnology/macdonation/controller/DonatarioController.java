package br.com.mactechnology.macdonation.controller;

import br.com.mactechnology.macdonation.dto.DtoDonatario;
import br.com.mactechnology.macdonation.exception.BusinessException;
import br.com.mactechnology.macdonation.mapper.DonatarioMapper;
import br.com.mactechnology.macdonation.model.Donatario;
import br.com.mactechnology.macdonation.service.DonatarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "donatario")
public class DonatarioController {

    @Autowired
    private DonatarioService donatarioService;

    @Autowired
    private DonatarioMapper donatarioMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody DtoDonatario dtoDonatario) {
        try {
            Donatario donatario = donatarioMapper.toEntity(dtoDonatario);
            Donatario donatarioSalvo = donatarioService.save(donatario);
            URI location = URI.create(String.format("donatario/%s", donatarioSalvo.getId()));
            return ResponseEntity.created(location).body(donatarioMapper.toDto(donatarioSalvo));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DtoDonatario>> read() {
        List<Donatario> donatarios = donatarioService.findAll();
        return ResponseEntity.ok(donatarioMapper.toCollectionDto(donatarios));
    }

    @GetMapping(value = "/{donatarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readById(@PathVariable Long donatarioId) {
        try {
            Donatario donatario = donatarioService.findById(donatarioId);
            return ResponseEntity.ok(donatarioMapper.toDto(donatario));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{donatarioId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Long donatarioId, @Valid @RequestBody DtoDonatario dtoDonatario) {
        try {
            if (!donatarioService.existsById(donatarioId)) {
                return ResponseEntity.notFound().build();
            }

            Donatario donatario = donatarioMapper.toEntity(dtoDonatario);
            donatario.setId(donatarioId);
            Donatario donatarioSalvo = donatarioService.save(donatario);

            return ResponseEntity.ok(donatarioMapper.toDto(donatarioSalvo));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{donatarioId}")
    public ResponseEntity<Void> delete(@PathVariable Long donatarioId) {
        if (!donatarioService.existsById(donatarioId)) {
            return ResponseEntity.notFound().build();
        }

        donatarioService.deleteById(donatarioId);
        return ResponseEntity.noContent().build();
    }
}