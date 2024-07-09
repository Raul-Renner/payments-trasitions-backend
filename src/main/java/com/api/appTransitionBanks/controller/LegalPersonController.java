package com.api.appTransitionBanks.controller;

import com.api.appTransitionBanks.dto.LegalPersonSaveDTO;
import com.api.appTransitionBanks.service.impl.LegalPersonServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.LegalPersonFieldQuery.valueOf;
import static java.util.Locale.getDefault;
import static java.util.ResourceBundle.getBundle;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person-legal")
@Tag(name = "Person with account legal", description = "account bank legal management APIs")
public class LegalPersonController {

    private final LegalPersonServiceImpl legalPersonService;


    @Operation(
            summary = "Added a new person legal",
            description = "This endpoint is used to added a new account bank to person legal",
            tags = {"save"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = LegalPersonSaveDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(defaultValue = "Server Error"))})
    })
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody LegalPersonSaveDTO legalPersonSaveDTO){
        var bundle = getBundle("ValidationMessages", getDefault());
        legalPersonService.save(legalPersonSaveDTO.toEntity());
        return ResponseEntity.ok(bundle.getString("user.cnpj.create.success"));
    }



    @Operation(
            summary = "Get account bank person legal",
            description = "Find account bank person legal as registered by: EMAIL, CNPJ and ID",
            tags = {"findBy", "GET"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account bank found with success",content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "No account bank person legal found"),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(defaultValue = "Server Error"))})
    })
    @GetMapping("/find-by")
    public ResponseEntity<?> findBy(@RequestParam String field, @RequestParam List<String> values) {
        log.info("Searching legal person user by {} = {}.", field, values);
        return ResponseEntity.ok(legalPersonService.findBy(valueOf(field).findBy(values)));
    }
}
