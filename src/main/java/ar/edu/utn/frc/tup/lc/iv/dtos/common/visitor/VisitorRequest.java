package ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor;

import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for creating or updating an authorized person.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitorRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * First name of the authorized person. Maximum of 50 characters.
     */
    @JsonProperty("name")
    private String name;

    /**
     * Last name of the authorized person. Maximum of 50 characters.
     */
    @JsonProperty("last_name")
    private String lastName;

    /**
     * Document type of the authorized person.
     */
    @JsonProperty("doc_type")
    private DocumentType documentType;

    /**
     * Document number of the authorized person.
     */
    @JsonProperty("doc_number")
    private Long docNumber;

    /**
     * Birthdate of the authorized person.
     */
    @JsonProperty("birth_date")
    @Schema(type = "string", pattern = "dd-MM-yyyy", example = "18-09-2024")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    /**
     * If a visitor is active or not.
     */
    @JsonProperty("is_active")
    private boolean isActive;

    @JsonProperty("visitor_type")
    private VisitorType visitorType;
}
