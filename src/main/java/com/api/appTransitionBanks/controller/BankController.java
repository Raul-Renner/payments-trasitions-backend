package com.api.appTransitionBanks.controller;

import com.api.appTransitionBanks.dto.TransferDTO;
import com.api.appTransitionBanks.service.impl.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

import static reactor.core.publisher.Mono.just;
import static com.api.appTransitionBanks.fieldQueries.BankAccountFieldQuery.valueOf;
@Slf4j
@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankAccountService bankAccountService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity authorizeTransfer(@Valid TransferDTO transferDTO) {
        return ResponseEntity.ok(bankAccountService.authorizeTransfer(transferDTO));
    }

    @CrossOrigin
    @GetMapping("exist")
    public Mono<?> existBy(@RequestParam String field, @RequestParam List<String> values) {
        return just(bankAccountService.existBy(valueOf(field).existBy(values)));
    }
}
