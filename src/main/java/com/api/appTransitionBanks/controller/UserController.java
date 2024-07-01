package com.api.appTransitionBanks.controller;

import com.api.appTransitionBanks.dto.PersonDTO;
import com.api.appTransitionBanks.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @CrossOrigin
    @DeleteMapping("{id}")
    public ResponseEntity delete(@RequestBody @Valid PersonDTO personDTO) {
        try {
            userService.deleteProfile(personDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("tes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
