package com.api.appTransitionBanks.controller;

import com.api.appTransitionBanks.dto.IndividualPersonSaveDTO;
import com.api.appTransitionBanks.entities.IndividualPerson;
import com.api.appTransitionBanks.service.impl.IndividualPersonServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.IndividualPersonFieldQuery.valueOf;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person-individual")
@Tag(name = "Person with account individual", description = "account bank individual management APIs")
public class IndividualPersonController {

    private final IndividualPersonServiceImpl individualPersonService;

    @Operation(
            summary = "Added a new person individual",
            description = "This endpoint is used to added a new account bank to person individual",
            tags = {"save"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = IndividualPersonSaveDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(defaultValue = "Server Error"))})
    })
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody IndividualPersonSaveDTO personSaveDTO){
            individualPersonService.save(personSaveDTO.toEntity());
            return ResponseEntity.ok(HttpStatus.OK);
    }


    @Operation(
            summary = "Get all person individual",
            description = "Get all account bank person individual registered in system",
            tags = {"findAll"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account bank found with success",content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "No account bank person individual found registered in system"),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(defaultValue = "Server Error"))})
    })
    @GetMapping
    public ResponseEntity<List<IndividualPerson>> findAll() {
        return ResponseEntity.ok(individualPersonService.findAll());
    }



    @Operation(
            summary = "Get person individual",
            description = "Find person individual as registered by: EMAIL, CPF and ID",
            tags = {"findBy", "GET"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account bank found with success",content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "No account bank person individual found"),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(defaultValue = "Server Error"))})
    })
    @GetMapping("/find-by")
    public ResponseEntity<?> findBy(@RequestParam String field, @RequestParam List<String> values) {
        log.info("Searching person individual user by {} = {}.", field, values);
        return ResponseEntity.ok(individualPersonService.findBy(valueOf(field).findBy(values)));
    }
}
