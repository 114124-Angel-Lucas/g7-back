package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.models.AuthRange;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Service interface for managing authorized ranges.
 * This interface defines the methods for handling
 * operations related to authorized ranges, including
 * creating, updating, retrieving, and deleting authorized ranges.
 */
public interface IAuthRangeService {
    /**
     * Retrieves all authorized ranges.
     * @return the list of authorized ranges
     */
    List<AuthRangeDTO> getAllAuthRanges();

    /**
     * Retrieves all authorized ranges.
     * @return the list of an authorization
     */
    List<AuthRangeDTO> getAuthRangesByAuth(AuthEntity authID);


    /**
     * Retrieves all authorized ranges.
     * @return the list of an authorization
     */
    List<AuthRangeDTO> getAuthRangesByAuthExternalID(Long externalID);

    /**
     * Registers a new authorized range.
     *
     * @param authRangeRequests the data transfer object containing the
     *  details of the authorized range to register.
     *  @return AuthorizedRanges
     */
    List<AuthRange> registerAuthRanges(List<AuthRangeRequestDTO> authRangeRequests,
                                 AuthEntity authEntity, VisitorDTO visitorDTO);

    /**
     * Checks if the given authorization range is valid.
     *
     * @param authRangeDTO the authorization range to check.
     * @param currentDate  current date.
     * @param currentTime  current time.
     * @return true if the authorization range is valid; false otherwise.
     */
    boolean isValidAuthRange(AuthRangeDTO authRangeDTO, LocalDate currentDate, LocalTime currentTime);

    /**
     * Retrieves a list of valid authorization ranges.
     *
     * @param authRanges  list of authorization ranges.
     * @param currentDate current date.
     * @param currentTime current time.
     * @return list of valid authorization ranges.
     */
    List<AuthRangeDTO> getValidAuthRanges(List<AuthRangeDTO> authRanges, LocalDate currentDate,
                                                 LocalTime currentTime);
}
