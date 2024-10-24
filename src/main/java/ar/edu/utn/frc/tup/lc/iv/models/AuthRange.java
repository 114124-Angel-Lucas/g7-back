package ar.edu.utn.frc.tup.lc.iv.models;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

import java.time.DayOfWeek;

import java.util.List;
import java.util.Arrays;

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
public class AuthRange extends AuditBase {
    /**
     * Unique identifier of the Acceses.
     */
    private Long authRangeId;
    /**
     * Date from to form the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    private LocalDate dateFrom;
    /**
     * Date until to form the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    private LocalDate dateTo;
    /**
     * Starting time for the authorized range on each day.
     * Defines the hour from which access is allowed.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime hourFrom;
    /**
     * Ending time for the authorized range on each day.
     * Defines the hour until which access is allowed.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime hourTo;
    /**
     * Days of the week for which the authorization is valid (Monday, Wednesday).
     */
    private List<DayOfWeek> daysOfWeek;

    /**
     * Additional comments or observations related to the authorization.
     */
    private String comment;

    /**
     * Indicates whether the authorization is currently active.
     */
    private boolean isActive;

    /**
     * Constructor that initializes AuthorizedRanges from AuthorizedRangesEntity.
     *
     * @param authorizedRangesEntity the AuthorizedRangesEntity object
     */
    public AuthRange(AuthRangeEntity authorizedRangesEntity) {
        this.authRangeId = authorizedRangesEntity.getAuthRangeId();
        this.dateFrom = authorizedRangesEntity.getDateFrom();
        this.dateTo = authorizedRangesEntity.getDateTo();
        this.hourFrom = authorizedRangesEntity.getHourFrom();
        this.hourTo = authorizedRangesEntity.getHourTo();
        this.daysOfWeek = Arrays.stream(authorizedRangesEntity.getDaysOfWeek().split(","))
                .map(String::toUpperCase)
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toList());
        this.comment = authorizedRangesEntity.getComment();
        this.isActive = authorizedRangesEntity.isActive();
    }
}
