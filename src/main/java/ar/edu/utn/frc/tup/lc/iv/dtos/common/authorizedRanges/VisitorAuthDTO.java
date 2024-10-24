package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object for creating visitor.
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorAuthDTO {
    /**
     * Unique identifier of the Acceses.
     */
    @JsonProperty("auth_range_id")
    private Long authRangeId;

    /**
     * Unique identifier of the visitor.
     */
    @JsonProperty("visitor_id")
    private VisitorDTO visitorId;

    /**
     * Date from to form the authorized range.
     */
    @JsonProperty("date_from")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    private LocalDate dateFrom;
    /**
     * Date until to form the authorized range.
     */
    @JsonProperty("date_to")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    private LocalDate dateTo;
    /**
     * Starting time for the authorized range on each day.
     * Defines the hour from which access is allowed.
     */
    @JsonProperty("hour_from")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime hourFrom;
    /**
     * Ending time for the authorized range on each day.
     * Defines the hour until which access is allowed.
     */
    @JsonProperty("hour_to")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime hourTo;
    /**
     * Days of the week for which the authorization is valid (Monday, Wednesday).
     */
    @JsonProperty("day_of_weeks")
    private String days;


    /**
     * Additional comments or observations related to the authorization.
     */
    @JsonProperty("comment")
    private String comment;

    /**
     * Indicates whether the authorization is currently active.
     */
    @JsonProperty("is_active")
    private boolean isActive;
}
