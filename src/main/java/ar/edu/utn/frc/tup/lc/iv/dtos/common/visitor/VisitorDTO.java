package ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor;

import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for the Authorized entity that represents an authorized person.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VisitorDTO {
    /**
     * Unique identifier of the Authorized.
     */
    @JsonProperty("visitor_id")
    private Long visitorId;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonProperty("birth_date")
    private LocalDate birthDate;

    /**
     * If a visitor is active or not.
     */
    @JsonProperty("is_active")
    private boolean isActive;
}
