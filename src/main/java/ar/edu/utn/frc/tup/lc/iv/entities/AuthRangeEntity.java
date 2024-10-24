package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.Entity;
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

import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "auth_ranges")
@Getter
@Setter
public class AuthRangeEntity extends AuditBaseEntity {
    /**
     * Unique identifier of the Acceses.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long authRangeId;
    /**
     * Unique Authorized identifier.
     */
    @ManyToOne
    @JoinColumn(name = "auth_id")
    private AuthEntity authId;
    /**
     * Date from to form the authorized range.
     */
    @Column(name = "date_from")
    private LocalDate dateFrom;
    /**
     * Date until to form the authorized range.
     */
    @Column(name = "date_to")
    private LocalDate dateTo;
    /**
     * The start time of the authorized range.
     */
    @Column(name = "hour_from")
    private LocalTime hourFrom;
    /**
     * The end time of the authorized range.
     */
    @Column(name = "hour_to")
    private LocalTime hourTo;
    /**
     * Days of the week when access is allowed (Monday, Tuesday,...).
     */
    @Column(name = "days")
    private String daysOfWeek;
    /**
     * Additional comments.
     */
    @Column(name = "comment")
    private String comment;
    /**
     * Status indicating if the range is active.
     */
    @Column(name = "is_active")
    private boolean isActive;
}
