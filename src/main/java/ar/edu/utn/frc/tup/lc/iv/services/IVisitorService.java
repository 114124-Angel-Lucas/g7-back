package ar.edu.utn.frc.tup.lc.iv.services;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;

/**
 * This interface defines the contract for a service
 * that manages authorized persons.
 * It provides a method to retrieve all authorized persons.
 */
public interface IVisitorService {
    /**
     * Retrieves a list of all authorized persons.
     * @param page the page number for pagination
     * @param size the size of the page
     * @return A list of {@link VisitorDTO} representing all authorized persons.
     */
    PaginatedResponse<VisitorDTO> getAllVisitors(int page, int size, String name, String lastName, String filter);

    List<VisitorDTO> getAllVisitors();

    /**
     * Creates a new visitor or update based on the provided data.
     *
     * @param authorizedRequestDto Object containing the necessary data
     *                             to create a new authorization.
     * @return An AuthorizedDTO object representing
     * the newly created authorization.
     */
    VisitorDTO saveOrUpdateVisitor(VisitorRequest authorizedRequestDto , Long visitorId);

    /**
     * This method finds and returns the visitor
     * associated with the given document number.
     *
     * @param docNumber The document number of the visitor.
     * @return VisitorDTO.
     */
    VisitorDTO getVisitorByDocNumber(Long docNumber);

    /**
     * Deactivates a visitor by their document number.
     *
     * @param docNumber The document number of the visitor to deactivate.
     * @return VisitorDTO representing the deactivated visitor.
     */
    VisitorDTO deleteVisitor(Long docNumber);

    /**
     * Get visitor by id.
     * @param id unique identifier of the visitor
     * @return visitorDto with the given id
     */
    VisitorDTO getVisitorById(Long id);
}
