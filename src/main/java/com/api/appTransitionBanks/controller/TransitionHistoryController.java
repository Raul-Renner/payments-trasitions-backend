package com.api.appTransitionBanks.controller;

import com.api.appTransitionBanks.dto.PersonDTO;
import com.api.appTransitionBanks.service.impl.TransitionsHistoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transitions")
public class TransitionHistoryController {

    private final TransitionsHistoryServiceImpl transitionsHistory;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<?> findBy(@Valid @RequestBody PersonDTO personDTO){
        return ResponseEntity.ok(transitionsHistory.findTransitionByNumberAccount(personDTO));
    }
}
