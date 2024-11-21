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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCandidate(@Valid CandidateDto candidateDto) {
        candidateService.createCandidate(candidateDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(CandidatesConstants.STATUS_201, CandidatesConstants.MESSAGE_201));
    }

}
