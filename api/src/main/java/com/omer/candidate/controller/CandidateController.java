package com.omer.candidate.controller;

import com.omer.candidate.constants.CandidatesConstants;
import com.omer.candidate.dto.CandidateDto;
import com.omer.candidate.dto.ErrorResponseDto;
import com.omer.candidate.dto.ResponseDto;
import com.omer.candidate.service.ICandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ömer ALTAN
 */

@Tag(
    name = "CRUD REST APIs for Candidates of Job-Vault Application",
    description = "CRUD REST APIs in Job-Vault Application to CREATE, UPDATE, FETCH AND DELETE candidate details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CandidateController {

    private final ICandidateService candidateService;

    public CandidateController(ICandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Operation(
        summary = "Create Candidate REST API",
        description = "REST API to create new Candidate"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    }
    )
    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> createCandidate(@Valid @ModelAttribute CandidateDto candidateDto) {
        candidateService.createCandidate(candidateDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(CandidatesConstants.STATUS_201, CandidatesConstants.MESSAGE_201));
    }

    @Operation(
        summary = "Delete Candidate REST API",
        description = "REST API to delete Candidate by id"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "417",
            description = "Expectation Failed"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCandidate(@RequestParam Long id) {
        boolean isDeleted = candidateService.deleteCandidate(id);
        if(isDeleted) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CandidatesConstants.STATUS_200, CandidatesConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(CandidatesConstants.STATUS_417, CandidatesConstants.MESSAGE_417_DELETE));
        }

    }

}
