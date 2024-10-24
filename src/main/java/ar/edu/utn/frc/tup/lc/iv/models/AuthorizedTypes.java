package ar.edu.utn.frc.tup.lc.iv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity class representing the types of authorization.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizedTypes extends AuditBase {
    /**
     * Unique identifier of the Authorized Types.
     */
    private Long authTypeId;
    /**
     * Description of Authorized type.
     */
    private String description;
}
