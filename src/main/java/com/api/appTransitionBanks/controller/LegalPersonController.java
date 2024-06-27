package com.api.appTransitionBanks.controller;

import com.api.appTransitionBanks.dto.LegalPersonSaveDTO;
import com.api.appTransitionBanks.service.impl.LegalPersonServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.LegalPersonFieldQuery.valueOf;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person-legal")
public class LegalPersonController {

    private final LegalPersonServiceImpl legalPersonService;



    @CrossOrigin
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody LegalPersonSaveDTO legalPersonSaveDTO){
        var bundle = getBundle("ValidationMessages", getDefault());
        legalPersonService.save(legalPersonSaveDTO.toEntity());
        return ResponseEntity.ok(bundle.getString("user.cnpj.create.success"));
    }

    @CrossOrigin
    @GetMapping("/find-by")
    public ResponseEntity<?> findBy(@RequestParam String field, @RequestParam List<String> values) {
        log.info("Searching legal person user by {} = {}.", field, values);
        return ResponseEntity.ok(legalPersonService.findBy(valueOf(field).findBy(values)));
    }
}
