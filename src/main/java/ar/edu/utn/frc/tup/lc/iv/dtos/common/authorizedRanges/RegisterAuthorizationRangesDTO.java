package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Data Transfer Object for registering authorized ranges.
 * This DTO is used to encapsulate the data required for
 * creating or updating an authorized range.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterAuthorizationRangesDTO {

    /**
     * Format for dates as "dd-MM-yyyy".
     */
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    /**
     * Format for time as "HH:mm:ss".
     */
    private static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * Represents the string data type.
     */
    private static final String STRING_TYPE = "string";

    /**
     * Unique Authorized identifier for the visitor.
     */
    @JsonProperty("visitor_id")
    private Long visitorId;

    /**
     * Unique Authorized entity identifier.
     */
    @JsonProperty("auth_entity_id")
    private Long authEntityId;

    /**
     * Date from which the authorized range is valid.
     */
    @JsonProperty("date_from")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @Schema(type = STRING_TYPE, pattern = DATE_FORMAT, example = "01-01-2022", description = "The start date of the authorized range.")
    private LocalDate dateFrom;

    /**
     * Date until which the authorized range is valid.
     */
    @JsonProperty("date_to")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @Schema(type = STRING_TYPE, pattern = DATE_FORMAT, example = "02-01-2024", description = "The end date of the authorized range.")
    private LocalDate dateTo;

    /**
     * The start time of the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
    @Schema(type = STRING_TYPE, pattern = TIME_FORMAT, example = "08:00", description = "The start time of the authorized range.")
    @JsonProperty("hour_from")
    private LocalTime hourFrom;

    /**
     * The end time of the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
    @Schema(type = STRING_TYPE, pattern = TIME_FORMAT, example = "17:00", description = "The end time of the authorized range.")
    @JsonProperty("hour_to")
    private LocalTime hourTo;

    /**
     * Days of the week when access is allowed (e.g., Monday, Tuesday,...).
     */
    @JsonProperty("days_of_week")
    private List<DayOfWeek> daysOfWeek;


    /**
     * Additional comments related to the authorized range.
     */
    private String comment;
}
