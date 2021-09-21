package br.com.mactechnology.macdonation.controller;

import br.com.mactechnology.macdonation.dto.DtoDoacao;
import br.com.mactechnology.macdonation.dto.DtoFamiliar;
import br.com.mactechnology.macdonation.dto.input.InputDoacao;
import br.com.mactechnology.macdonation.dto.input.InputFamiliar;
import br.com.mactechnology.macdonation.mapper.FamiliarMapper;
import br.com.mactechnology.macdonation.model.Doacao;
import br.com.mactechnology.macdonation.model.Familiar;
import br.com.mactechnology.macdonation.service.DonatarioService;
import br.com.mactechnology.macdonation.service.FamiliarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "donatario/{donatarioId}")
public class FamiliarController {

    @Autowired
    private DonatarioService donatarioService;

    @Autowired
    private FamiliarService familiarService;

    @Autowired
    private FamiliarMapper familiarMapper;

    @PostMapping(value = "/familiar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DtoFamiliar> create(@PathVariable Long donatarioId, @RequestBody InputFamiliar inputFamiliar) {
        if (!donatarioService.existsById(donatarioId)) {
            return ResponseEntity.notFound().build();
        }

        Familiar familiar = familiarMapper.toEntity(inputFamiliar);
        familiar.setDonatario(donatarioService.findById(donatarioId));

        Familiar familiarSalvo = familiarService.save(familiar);
        return ResponseEntity.ok(familiarMapper.toDto(familiarSalvo));
    }

    @GetMapping(value = "/familiar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DtoFamiliar>> read(@PathVariable Long donatarioId) {
        if (!donatarioService.existsById(donatarioId)) {
            return ResponseEntity.notFound().build();
        }

        List<Familiar> familiares = familiarService.findByDonatarioId(donatarioId);
        return ResponseEntity.ok(familiarMapper.toCollectionDto(familiares));
    }

    @GetMapping(value = "/familiar/{familiarId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoFamiliar> readById(@PathVariable Long familiarId) {
        Familiar familiar = familiarService.findById(familiarId);
        return ResponseEntity.ok(familiarMapper.toDto(familiar));
    }

    @PutMapping(value = "/familiar/{familiarId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoFamiliar> update(@PathVariable Long donatarioId, @PathVariable Long familiarId, @RequestBody InputFamiliar inputFamiliar) {
        if (!donatarioService.existsById(donatarioId) || !familiarService.existsById(familiarId)) {
            return ResponseEntity.notFound().build();
        }

        Familiar familiar = familiarMapper.toEntity(inputFamiliar);
        familiar.setDonatario(donatarioService.findById(donatarioId));
        familiar.setId(familiarId);

        Familiar familiarSalvo = familiarService.save(familiar);
        return ResponseEntity.ok(familiarMapper.toDto(familiarSalvo));
    }

    @DeleteMapping(value = "/familiar/{familiarId}")
    public ResponseEntity<Void> delete(@PathVariable Long familiarId) {
        if (!familiarService.existsById(familiarId)) {
            return ResponseEntity.notFound().build();
        }

        familiarService.deleteById(familiarId);
        return ResponseEntity.noContent().build();
    }
}