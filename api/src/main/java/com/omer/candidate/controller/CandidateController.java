package com.omer.candidate.controller;

import com.omer.candidate.constants.CandidatesConstants;
import com.omer.candidate.dto.CandidateDto;
import com.omer.candidate.dto.CandidateFilterDto;
import com.omer.candidate.dto.ErrorResponseDto;
import com.omer.candidate.dto.ResponseDto;
import com.omer.candidate.service.ICandidateService;
import com.omer.candidate.service.IFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
    private final IFileService fileService;

    public CandidateController(ICandidateService candidateService, IFileService fileService) {
        this.candidateService = candidateService;
        this.fileService = fileService;
    }

    @Operation(
        summary = "Fetch Candidate Details by ID",
        description = "This API retrieves the candidate details by their unique ID."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Successfully fetched candidate details",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = CandidateDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Candidate not found for the given ID",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error, unexpected condition encountered",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @GetMapping("/candidates/{id}")
    public ResponseEntity<CandidateDto> fetchCandidateById(@PathVariable Long id){
        CandidateDto candidateDto = candidateService.fetchCandidate(id);
        return ResponseEntity.ok(candidateDto);
    }

    @Operation(
        summary = "Fetch all candidates with pagination",
        description = "Fetches a paginated list of candidates. Throws a 404 error if no candidates are found."
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
    public ResponseEntity<Page<CandidateDto>> fetchAllCandidates(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CandidateDto> candidates = candidateService.fetchAllCandidates(pageable);
        return ResponseEntity.ok(candidates);
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
    @PostMapping(value = "/candidates", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
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
    @PutMapping("/candidates/{id}")
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
    @DeleteMapping("/candidates/{id}")
    public ResponseEntity<ResponseDto> deleteCandidate(@PathVariable Long id) {
        boolean isDeleted = candidateService.deleteCandidate(id);
        if(isDeleted) {
            return status(HttpStatus.OK)
                .body(new ResponseDto(CandidatesConstants.STATUS_200, CandidatesConstants.MESSAGE_200));
        }else {
            return status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(CandidatesConstants.STATUS_417, CandidatesConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/candidates/filter")
    @Operation(
        summary = "Fetch Candidates with Filters",
        description = "Fetch candidates based on optional filters like position type, military status, and notice period. " +
            "Available values for filters are provided below.",
        parameters = {
            @Parameter(
                name = "positionType",
                description = "Filter candidates by position type",
                example = "BACKEND_DEVELOPER",
                schema = @Schema(allowableValues = {"BACKEND_DEVELOPER", "FRONTEND_DEVELOPER", "FULL_STACK_DEVELOPER"})
            ),
            @Parameter(
                name = "militaryStatus",
                description = "Filter candidates by military status",
                example = "DONE",
                schema = @Schema(allowableValues = {"DONE", "NOT_DONE"})
            ),
            @Parameter(
                name = "noticePeriod",
                description = "Filter candidates by notice period",
                example = "FIVE_DAYS",
                schema = @Schema(allowableValues = {"FIVE_DAYS", "TEN_DAYS", "ONE_WEEK", "TWO_WEEKS", "THREE_WEEKS", "ONE_MONTH"})
            )
        }
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Candidates fetched successfully",
            content = @Content(schema = @Schema(implementation = CandidateDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No candidates found matching the filters",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
        )
    })
    public ResponseEntity<List<CandidateFilterDto>> fetchCandidatesWithFilters(
        @RequestParam(required = false) String positionType,
        @RequestParam(required = false) String militaryStatus,
        @RequestParam(required = false) String noticePeriod) {

        List<CandidateFilterDto> candidates = candidateService.fetchCandidatesWithFilters(positionType, militaryStatus, noticePeriod);
        return ResponseEntity.ok(candidates);
    }

    @GetMapping("/candidates/download-cv/{fileName}")
    public ResponseEntity<Resource> downloadCv(@PathVariable String fileName) {
        Resource resource = fileService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }

}
