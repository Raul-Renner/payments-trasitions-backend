package com.api.appTransitionBanks.controller;

import com.api.appTransitionBanks.dto.TransferDTO;
import com.api.appTransitionBanks.service.impl.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.BankAccountFieldQuery.valueOf;

@Slf4j
@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankAccountService bankAccountService;

    @CrossOrigin
    @GetMapping("authorize")
    public ResponseEntity authorizeTransfer(@Valid @RequestBody TransferDTO transferDTO) {
        return ResponseEntity.ok(bankAccountService.authorizeTransfer(transferDTO));
    }

    @CrossOrigin
    @GetMapping("exist")
    public ResponseEntity<?> existBy(@RequestParam String field, @RequestParam List<String> values) {
        return ResponseEntity.ok(bankAccountService.existBy(valueOf(field).existBy(values)));
    }

    @CrossOrigin
    @PostMapping("transfer-realize")
    public ResponseEntity realizeTransfer(@RequestBody @Valid TransferDTO transferDTO) {
        bankAccountService.executetTransfer(transferDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/find-by")
    public ResponseEntity<?> findBy(@RequestParam String field, @RequestParam String value) {
        log.info("Searching account user by {} = {}.", field, value);
        return ResponseEntity.ok(bankAccountService.findBy(valueOf(field).findBy(value)));
    }
}

