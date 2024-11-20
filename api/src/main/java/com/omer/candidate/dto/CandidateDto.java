package com.omer.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(name = "Candidate",
    description = "Schema to hold Candidate information"
)
@Data
public class CandidateDto {

    @NotEmpty(message = "First Name can not be a null or empty")
    @Size(min = 3, max = 20, message = "First Name must be between 2 and 50 characters")
    @Schema(
        description = "First Name of Customer", example = "Ömer"
    )
    private String firstName;
    @NotEmpty(message = "Last Name can not be a null or empty")
    @Size(min = 3, max = 20, message = "Last Name must be between 2 and 50 characters")
    @Schema(
        description = "Last Name of Customer", example = "ALTAN"
    )
    private String lastName;
    @NotEmpty(message = "Position Type can not be a null or empty")
    @Schema(
        description = "Position type of the candidate",
        example = "BACKEND_DEVELOPER",
        allowableValues = {"BACKEND_DEVELOPER", "FRONTEND_DEVELOPER", "FULL_STACK_DEVELOPER"}
    )
    private String positionType;
    @NotEmpty(message = "Military Status can not be a null or empty")
    @Schema(
        description = "Military status of the candidate",
        example = "DONE",
        allowableValues = {"DONE", "NOT_DONE"}
    )
    private String militaryStatus;
    @NotEmpty(message = "Notice Period can not be a null or empty")
    @Schema(
        description = "Notice period of the candidate",
        example = "FIVE_DAYS",
        allowableValues = {"FIVE_DAYS", "TEN_DAYS", "ONE_WEEK", "TWO_WEEKS", "THREE_WEEKS", "ONE_MONTH"}
    )
    private String noticePeriod;
    @NotEmpty(message = "Email can not be a null or empty")
    @Email(message = "Invalid email format")
    @Schema(description = "Email of the candidate", example = "omeraltan@gmail.com")
    private String email;
    @NotEmpty(message = "Phone can not be a null or empty")
    @Pattern(regexp = "^\\d{4}-\\d{3}-\\d{2}-\\d{2}$", message = "Invalid phone number format. Expected format: 0544-345-32-12")
    @Schema(description = "Phone number of the candidate", example = "0544-345-32-12")
    private String phone;
    @NotEmpty(message = "CV Path can not be a null or empty")
    @Schema(description = "CV path of the candidate", example = "/uploads/cv_123.pdf")
    private String cvPath;
}
