package com.api.appTransitionBanks.controller;

import com.api.appTransitionBanks.dto.DepositeDTO;
import com.api.appTransitionBanks.dto.TransferDTO;
import com.api.appTransitionBanks.service.impl.BankAccountService;
import com.api.appTransitionBanks.validation.ValidBalance;
import com.api.appTransitionBanks.validation.ValidNumberAccount;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.api.appTransitionBanks.fieldQueries.BankAccountFieldQuery.valueOf;
import static java.util.Locale.getDefault;
import static java.util.Objects.nonNull;
import static java.util.ResourceBundle.getBundle;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
@Tag(name = "account bank", description = "account bank management APIs")
public class BankController {

    private final BankAccountService bankAccountService;

    @Operation(
            summary = "Get bank authorization",
            description = "This endpoint is used bank authorization to realize transitions",
            tags = {"authorizeTransfer", "GET"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "No account bank found registered in system"),
            @ApiResponse(responseCode = "200", description = "bank authorization realize with success",content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(defaultValue = "Server Error"))})
    })
    @GetMapping("authorize")
    public ResponseEntity authorizeTransfer(@Valid @ValidNumberAccount @RequestParam String numberAccountSender,
                                            @Valid @ValidBalance @RequestParam Double valueTransfer) {
        var result = bankAccountService.authorizeTransfer(numberAccountSender, valueTransfer);
        return result ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }



    @Operation(
            summary = "exist of informed bank account",
            description = "This endpoint is used to verification of existence of informed bank account",
            tags = {"existBy", "GET"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "account bank informed no exist registered in system"),
            @ApiResponse(responseCode = "200", description = "account bank informed exist",content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(defaultValue = "Server Error"))})
    })
    @GetMapping("exist")
    public ResponseEntity<?> existBy(@RequestParam String field, @RequestParam List<String> values) {
        var result = bankAccountService.existBy(valueOf(field).existBy(values));
        return result ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }



    @Operation(
            summary = "Get account bank authorization",
            description = "This endpoint is used bank authorization to realize transitions",
            tags = {"realizeTransfer", "POST"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "account bank informed no exist registered in system"),
            @ApiResponse(responseCode = "200", description = "account bank informed exist",content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(defaultValue = "Server Error"))})
    })
    @PostMapping("transfer-realize")
    public ResponseEntity realizeTransfer(@RequestBody @Valid TransferDTO transferDTO) {
        bankAccountService.executetTransfer(transferDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }



    @Operation(
            summary = "Get account bank",
            description = "This endpoint is used to find a account bank registered system by: ACCOUNT",
            tags = {"findBy", "GET"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "No account bank found registered in system"),
            @ApiResponse(responseCode = "200", description = "Account bank found with success",content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(defaultValue = "Server Error"))})
    })
    @GetMapping("/find-by")
    public ResponseEntity<?> findBy(@RequestParam String field, @RequestParam List<String> values) {
        try {
            var bundle = getBundle("ValidationMessages", getDefault());
            log.info("Searching account user by {} = {}.", field, values);
            var result = bankAccountService.findBy(valueOf(field).findBy(values));
            return nonNull(result) ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(bundle.getString("user.notfound"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }



    @Operation(
            summary = "Realize bank deposit",
            description = "This endpoint is used to realize a deposit to any type of account registered in system",
            tags = {"deposite", "POST"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = DepositeDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(defaultValue = "Server Error"))})
    })
    @PostMapping("deposite")
    public ResponseEntity deposite(@RequestBody @Valid DepositeDTO depositeDTO) {
        bankAccountService.realizeDeposite(depositeDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Delete account bank by number account",
            description = "Delete account bank person by number account registered in system",
            tags = {"delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account bank DELETE with success",content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "No account bank registered in system"),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(defaultValue = "Server Error"))})
    })
    @DeleteMapping("{numberAccount}")
    public ResponseEntity delete(@PathVariable @Valid String numberAccount) {
        try {
            bankAccountService.delete(numberAccount);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deletar Account Bank", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

