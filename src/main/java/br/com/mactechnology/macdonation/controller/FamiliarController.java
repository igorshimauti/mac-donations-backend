package br.com.mactechnology.macdonation.controller;

import br.com.mactechnology.macdonation.dto.DtoFamiliar;
import br.com.mactechnology.macdonation.exception.BusinessException;
import br.com.mactechnology.macdonation.mapper.FamiliarMapper;
import br.com.mactechnology.macdonation.model.Familiar;
import br.com.mactechnology.macdonation.service.DonatarioService;
import br.com.mactechnology.macdonation.service.FamiliarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "donatario/{donatarioId}/familiar")
public class FamiliarController {

    @Autowired
    private DonatarioService donatarioService;

    @Autowired
    private FamiliarService familiarService;

    @Autowired
    private FamiliarMapper familiarMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@PathVariable Long donatarioId, @RequestBody DtoFamiliar dtoFamiliar) {
        if (!donatarioService.existsById(donatarioId)) {
            return ResponseEntity.notFound().build();
        }

        try {
            Familiar familiar = familiarMapper.toEntity(dtoFamiliar);
            familiar.setDonatario(donatarioService.findById(donatarioId));
            Familiar familiarSalvo = familiarService.save(familiar);
            URI location = URI.create(String.format("donatario/%s/familiar/%s", donatarioId, familiarSalvo.getId()));
            return ResponseEntity.created(location).body(familiarMapper.toDto(familiarSalvo));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DtoFamiliar>> read(@PathVariable Long donatarioId) {
        if (!donatarioService.existsById(donatarioId)) {
            return ResponseEntity.notFound().build();
        }

        List<Familiar> familiares = familiarService.findByDonatarioId(donatarioId);
        return ResponseEntity.ok(familiarMapper.toCollectionDto(familiares));
    }

    @GetMapping(value = "/{familiarId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readById(@PathVariable Long donatarioId, @PathVariable Long familiarId) {
        if (!donatarioService.existsById(donatarioId) || !familiarService.existsById(familiarId)) {
            return ResponseEntity.notFound().build();
        }

        try {
            Familiar familiar = familiarService.findById(donatarioId, familiarId);
            return ResponseEntity.ok(familiarMapper.toDto(familiar));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{familiarId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Long donatarioId, @PathVariable Long familiarId, @RequestBody DtoFamiliar dtoFamiliar) {
        if (!donatarioService.existsById(donatarioId) || !familiarService.existsById(familiarId)) {
            return ResponseEntity.notFound().build();
        }

        try {
            Familiar familiar = familiarMapper.toEntity(dtoFamiliar);
            familiar.setDonatario(donatarioService.findById(donatarioId));
            familiar.setId(familiarId);

            Familiar familiarSalvo = familiarService.save(familiar);
            return ResponseEntity.ok(familiarMapper.toDto(familiarSalvo));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{familiarId}")
    public ResponseEntity<?> delete(@PathVariable Long donatarioId, @PathVariable Long familiarId) {
        if (!donatarioService.existsById(donatarioId) || !familiarService.existsById(familiarId)) {
            return ResponseEntity.notFound().build();
        }

        try {
            familiarService.deleteById(donatarioId, familiarId);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}