package ar.edu.utn.frc.tup.lc.iv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity class representing an authorized person.
 * Each authorized person has personal details such as name,
 * last name, document number and birthdate
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Visitors  extends AuditBase {
    /**
     * Unique identifier of the Authorized.
     */
    private Long visitorId;
    /**
     * Name of the authorized person.
     */
    private String name;
    /**
     * LastName of the authorized person.
     */
    private String lastName;
    /**
     * Document Number of the authorized person.
     */
    private Long docNumber;
    /**
     * Birth Date of the authorized person.
     */
   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    private LocalDate birthDate;
    /**
     * Identifier of the owner who authorizes an operation.
     */
    private Long ownerId;
    /**
     * If a visitor is active or not.
     */
    private boolean isActive;
}
