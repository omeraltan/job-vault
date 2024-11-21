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

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

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
        summary = "Fetch all candidates",
        description = "Fetches a list of all candidates. Throws a 404 error if no candidates are found."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "List of candidates fetched successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = CandidateDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No candidates found",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ResponseDto.class)
            )
        )
    })
    @GetMapping("/candidates")
    public ResponseEntity<List<CandidateDto>> fetchAllCandidates() {
        return ResponseEntity.ok(candidateService.fetchAllCandidates());
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
        return status(HttpStatus.CREATED)
            .body(new ResponseDto(CandidatesConstants.STATUS_201, CandidatesConstants.MESSAGE_201));
    }

    @Operation(
        summary = "Update Candidate Details REST API",
        description = "REST API to update candidate details, including CV upload (PDF only, max size 2 MB)",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Candidate details including CV file",
            required = true,
            content = @Content(
                mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                schema = @Schema(implementation = CandidateDto.class)
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully updated"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDto> updateCandidate(@PathVariable Long id, @Valid @ModelAttribute CandidateDto candidateDto) {
        candidateService.updateCandidate(id, candidateDto);
        return status(HttpStatus.OK)
            .body(new ResponseDto(CandidatesConstants.STATUS_200, CandidatesConstants.MESSAGE_200));
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
            return status(HttpStatus.OK)
                .body(new ResponseDto(CandidatesConstants.STATUS_200, CandidatesConstants.MESSAGE_200));
        }else {
            return status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(CandidatesConstants.STATUS_417, CandidatesConstants.MESSAGE_417_DELETE));
        }

    }

}
