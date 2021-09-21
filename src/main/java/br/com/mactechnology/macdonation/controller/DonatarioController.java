package br.com.mactechnology.macdonation.controller;

import br.com.mactechnology.macdonation.dto.DtoDonatario;
import br.com.mactechnology.macdonation.dto.input.InputDonatario;
import br.com.mactechnology.macdonation.mapper.DonatarioMapper;
import br.com.mactechnology.macdonation.model.Donatario;
import br.com.mactechnology.macdonation.service.DonatarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DtoDonatario> createDonatario(@Valid @RequestBody InputDonatario inputDonatario) {
        Donatario donatario = donatarioMapper.toEntity(inputDonatario);
        Donatario donatarioSalvo = donatarioService.save(donatario);
        return ResponseEntity.ok(donatarioMapper.toDto(donatarioSalvo));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DtoDonatario>> readDonatarios() {
        List<Donatario> donatarios = donatarioService.findAll();
        return ResponseEntity.ok(donatarioMapper.toCollectionDto(donatarios));
    }

    @GetMapping(value = "/{donatarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoDonatario> readDonatarioById(@PathVariable Long donatarioId) {
        Donatario donatario = donatarioService.findById(donatarioId);
        return ResponseEntity.ok(donatarioMapper.toDto(donatario));
    }

    @PutMapping(value = "/{donatarioId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoDonatario> updateDonatario(@PathVariable Long donatarioId, @Valid @RequestBody InputDonatario inputDonatario) {
        if (!donatarioService.existsById(donatarioId)) {
            return ResponseEntity.notFound().build();
        }

        Donatario donatario = donatarioMapper.toEntity(inputDonatario);
        donatario.setId(donatarioId);

        Donatario donatarioSalvo = donatarioService.save(donatario);
        return ResponseEntity.ok(donatarioMapper.toDto(donatarioSalvo));
    }

    @DeleteMapping(value = "/{donatarioId}")
    public ResponseEntity<Void> deleteDonatario(@PathVariable Long donatarioId) {
        if (!donatarioService.existsById(donatarioId)) {
            return ResponseEntity.notFound().build();
        }

        donatarioService.deleteById(donatarioId);
        return ResponseEntity.noContent().build();
    }
}