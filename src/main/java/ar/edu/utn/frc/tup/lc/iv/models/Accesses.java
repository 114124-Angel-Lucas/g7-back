package ar.edu.utn.frc.tup.lc.iv.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing the accesses to the neighborhood.
 * Each access record captures details about the entry and exit
 * of vehicles,including the vehicle type, registration, and any
 * relevant comments or observations.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Accesses extends AuditBase {
    /**
     * Unique identifier of the Accesses.
     */
    private Long accessId;
    /**
     * The authorized range associated with the access.
     */
    private Long authRangeId;
    /**
     * Date and time of entry to the neighborhood.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private LocalDateTime entryDate;
    /**
     * Date and time of departure to the neighborhood.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private LocalDateTime exitDate;
    /**
     * Unique identifier for the vehicle type associated with the access.
     */
    private VehicleTypes vehicleType;
    /**
     * Vehicle registration.
     */
    private String vehicleReg;
    /**
     * Vehicle Description.
     */
    private String vehicleDescription;
    /**
     * Visitor associated with the access.
     */
    private Long visitorId;
    /**
     * Observations on access.
     */
    private String comments;
}
