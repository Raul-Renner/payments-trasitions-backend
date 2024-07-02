package com.api.appTransitionBanks.controller;

import com.api.appTransitionBanks.dto.DepositeDTO;
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
@CrossOrigin
@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankAccountService bankAccountService;


    @GetMapping("authorize")
    public ResponseEntity authorizeTransfer(@Valid @RequestBody TransferDTO transferDTO) {
        var result = bankAccountService.authorizeTransfer(transferDTO);
        return result ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }


    @GetMapping("exist")
    public ResponseEntity<?> existBy(@RequestParam String field, @RequestParam List<String> values) {
        var result = bankAccountService.existBy(valueOf(field).existBy(values));
        return result ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }


    @PostMapping("transfer-realize")
    public ResponseEntity realizeTransfer(@RequestBody @Valid TransferDTO transferDTO) {
        bankAccountService.executetTransfer(transferDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("/find-by")
    public ResponseEntity<?> findBy(@RequestParam String field, @RequestParam String value) {
        log.info("Searching account user by {} = {}.", field, value);
        return ResponseEntity.ok(bankAccountService.findBy(valueOf(field).findBy(value)));
    }

    @PostMapping("deposite")
    public ResponseEntity deposite(@RequestBody @Valid DepositeDTO depositeDTO) {
        bankAccountService.realizeDeposite(depositeDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

