package com.api.appTransitionBanks.controller;

import com.api.appTransitionBanks.dto.IndividualPersonSaveDTO;
import com.api.appTransitionBanks.entities.IndividualPerson;
import com.api.appTransitionBanks.service.impl.IndividualPersonServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person-individual")
public class IndividualPersonController {

    private final IndividualPersonServiceImpl individualPersonService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody IndividualPersonSaveDTO personSaveDTO){
            individualPersonService.save(personSaveDTO.toEntity());
            return ResponseEntity.ok(HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable String id) {
        try {
            individualPersonService.deleteAccount(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("tes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<IndividualPerson>> findAll() {
        return ResponseEntity.ok(individualPersonService.findAll());
    }
}
