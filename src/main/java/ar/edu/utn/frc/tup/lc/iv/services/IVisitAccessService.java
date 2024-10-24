package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface for the Visit Access Service, which defines
 * methods for managing and retrieving visit access records.
 */
public interface IVisitAccessService {

    /**
     * Retrieves a list of visit access records.
     * @param plotId the ID of the plot for which to retrieve visit access records
     * @param visitorId the ID of the visitor for whom to retrieve access records
     * @param dateFrom the start date of the range for filtering access records
     * @param dateTo the end date of the range for filtering access records
     * @return a list of {@link AccessDTO} visit access records for a visitor
     */
    List<AccessDTO> getVisitAccessesByID(Long plotId, Long visitorId, LocalDate dateFrom, LocalDate dateTo);

    /**
     * Retrieves a list of visit access records.
     * @param plotId the ID of the plot for which to retrieve visit access records
     * @param dateFrom the start date of the range for filtering access records
     * @param dateTo the end date of the range for filtering access records
     * @return a list of {@link AccessDTO} visit access records for a plot
     */
    List<AccessDTO> getVisitAccesses(Long plotId, LocalDate dateFrom, LocalDate dateTo);

    /**
     * Maps an {@link AccessEntity} to a {@link AccessDTO}.
     *
     * @param accessEntity the entity to be mapped
     * @return a {@link AccessDTO} representing the mapped access entity
     */
    AccessDTO mapVisitAccessDTO(AccessEntity accessEntity);
}
