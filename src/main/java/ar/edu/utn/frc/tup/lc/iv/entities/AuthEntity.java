package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;

/**
 * Entity class representing the authorized ranges.
 * The authorized ranges define a time frame for which an
 * authorized person is allowed access, and it is linked
 * to the authorized type the person.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "auths")
@Getter
@Setter
public class AuthEntity extends AuditBaseEntity {
    /**
     * Unique identifier of the Acceses.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long authId;
    /**
     * Unique Authorized identifier.
     */
    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private VisitorEntity visitor;
    /**
     * Unique plot identifier.
     */
    private Long plotId;
    /**
     * Type of visitor.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "visitor_type")
    private VisitorType visitorType;
    /**
     * External Id.
     */
    @Column(name = "external_id")
    private Long externalID;
    /**
     * Status indicating if the range is active.
     */
    @Column(name = "is_active")
    private boolean isActive;

    public AuthEntity(Long createdUser, Long lastUpdatedUser) {
        super(createdUser, lastUpdatedUser);
    }
}
