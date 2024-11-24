package com.omer.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "CandidateFilter",
    description = "Schema to filter candidates by optional criteria and include CV for download"
)
@Data
public class CandidateFilterDto {

    @Schema(description = "Id of the candidate", example = "1")
    private Long id;

    @Schema(description = "First Name of the candidate", example = "Ömer")
    private String firstName;

    @Schema(description = "Last Name of the candidate", example = "ALTAN")
    private String lastName;

    @Schema(
        description = "Position type of the candidate",
        example = "BACKEND_DEVELOPER",
        allowableValues = {"BACKEND_DEVELOPER", "FRONTEND_DEVELOPER", "FULL_STACK_DEVELOPER"}
    )
    private String positionType;

    @Schema(
        description = "Military status of the candidate",
        example = "DONE",
        allowableValues = {"DONE", "NOT_DONE"}
    )
    private String militaryStatus;

    @Schema(
        description = "Notice period of the candidate",
        example = "FIVE_DAYS",
        allowableValues = {"FIVE_DAYS", "TEN_DAYS", "ONE_WEEK", "TWO_WEEKS", "THREE_WEEKS", "ONE_MONTH"}
    )
    private String noticePeriod;

    @Schema(description = "Email address of the candidate", example = "omeraltan@gmail.com")
    private String email;

    @Schema(description = "Phone number of the candidate", example = "0544-345-32-12")
    private String phone;

    @Schema(description = "Path to the candidate's CV file", example = "/resumes/omer_altan_12345.pdf")
    private String cvPath;

    @Schema(description = "Base64-encoded content of the CV file", example = "JVBERi0xLjQKJb... (truncated)")
    private String cvContent;
}
