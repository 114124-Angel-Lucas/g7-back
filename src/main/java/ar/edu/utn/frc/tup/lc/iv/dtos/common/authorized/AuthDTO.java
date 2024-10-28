package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  DTO for the Authorized entity
 *  that represents an authorized person.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthDTO {
    /**
     * ID of the authorized person.
     */
    @JsonProperty("auth_id")
    private Long authId;
    /**
     * ID of the authorized person.
     */
    @JsonProperty("plot_id")
    private Long plotId;

    @JsonProperty("authorizer_id")
    private Long authorizerId;
    /**
     * DTO of the authorized person.
     */
    @JsonProperty("visitor")
    private VisitorDTO visitor;

    /**
     * Type of the authorized person.
     */
    @JsonProperty("visitor_type")
    private VisitorType visitorType;

    /**
     * Type of the authorized person.
     */
    @JsonProperty("external_id")
    private Long externalID;

    /**
     * List of authorized ranges.
     */
    @JsonProperty("auth_ranges")
    private List<AuthRangeDTO> authRanges;

    /**
     * Status of the authorized person.
     */
    @JsonProperty("is_active")
    private boolean isActive;

}
