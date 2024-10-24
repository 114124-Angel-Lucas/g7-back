package ar.edu.utn.frc.tup.lc.iv.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The {@code BaseEntity} class is a base class
 * that provides common auditing fields
 * for entities, such as the user who created
 * or last updated the entity, and the
 * respective timestamps.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class AuditBase {
    /**
     * The ID of the user who created the entity.
     */
    private Long createdUser;

    /**
     * The date and time when the entity was created.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private LocalDateTime createdDate;

    /**
     * The ID of the user who last updated the entity.
     */
    private Long lastUpdatedUser;

    /**
     * The date and time when the entity was last updated.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private LocalDateTime lastUpdatedDate;
}
