package com.api.appTransitionBanks.controller;

import com.api.appTransitionBanks.entities.Person;
import com.api.appTransitionBanks.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

//    @CrossOrigin
//    @DeleteMapping("{id}")
//    public ResponseEntity delete(@PathVariable String id) {
//        try {
//            userService.deleteAccount(id);
//            return ResponseEntity.ok(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("tes", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
        @CrossOrigin
        @GetMapping
        public ResponseEntity<List<Person>> findAll() {
            return ResponseEntity.ok(userService.findAll());
        }
}
