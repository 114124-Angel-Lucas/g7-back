package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import java.time.DayOfWeek;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object for creating or updating an authorized range.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRangeRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * Date from to form the authorized range.
     */
    @JsonProperty("date_from")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @Schema(type = STRING_TYPE, pattern = DATE_FORMAT, example = "01-01-2022", description = "The start date of the authorized range.")
    private LocalDate dateFrom;

    /**
     * Date until to form the authorized range.
     */
    @JsonProperty("date_to")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @Schema(type = STRING_TYPE, pattern = DATE_FORMAT, example = "02-01-2024", description = "The end date of the authorized range.")
    private LocalDate dateTo;

    /**
     * Hour from to form the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
    @Schema(type = STRING_TYPE, pattern = TIME_FORMAT, example = "08:00:00", description = "The start time of the authorized range.")
    @JsonProperty("hour_from")
    private LocalTime hourFrom;

    /**
     * Hour until to form the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
    @Schema(type = STRING_TYPE, pattern = TIME_FORMAT, example = "17:00:00", description = "The end time of the authorized range.")
    @JsonProperty("hour_to")
    private LocalTime hourTo;

    /**
     * Days of the week to form the authorized range.
     */
    @JsonProperty("days_of_week")
    private List<DayOfWeek> daysOfWeek;

    /**
     * Id of the plot.
     */
    @JsonProperty("plot_id")
    private Long plotId;

    /**
     * Comment of the authorized range.
     */
    @JsonProperty("comment")
    private String comment;

    /**
     * Status of the authorized range.
     */
    @JsonProperty("is_active")
    private boolean isActive;
}
