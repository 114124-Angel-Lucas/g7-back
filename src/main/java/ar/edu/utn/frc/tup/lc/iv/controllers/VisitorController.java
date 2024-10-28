package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;
import ar.edu.utn.frc.tup.lc.iv.services.IVisitorService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
 * Controller class for managing authorized persons.
 * This class provides endpoints to create, retrieve,
 * and update authorized persons.
 */
@RestController
@RequestMapping("/visitors")
public class VisitorController {

    /**
     * Authorized Service dependency injection.
     */
    @Autowired
    private IVisitorService visitorService;

    /**
     * Retrieves a list of all visitors.
     *
     * @param page the page number for pagination
     * @param size the size of the page
     * @return a list of VisitorDTO objects
     */
    @GetMapping()
    public List<VisitorDTO> getAllVisitors(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String lastName,
                                                        @RequestParam(required = false) String filter) {
        return visitorService.getAllVisitors();
        //return visitorService.getAllVisitors(page, size, name, lastName, filter);
    }

    /**
     * Retrieves a specific authorized person by their ID.
     *
     * @param docNumber The identifier of the visitor person.
     * @return The VisitorDto object representing the authorized
     *         person with the specified ID.
     */
    @GetMapping("/by-doc-number/{docNumber}")
    public ResponseEntity<VisitorDTO> getVisitorByDocNumber(@PathVariable Long docNumber) {
        return ResponseEntity.ok(visitorService.getVisitorByDocNumber(docNumber));
    }

    /**
     * Updates an existing visitor or create a new visitor.
     *
     * @param visitorRequest The DTO containing the details
     *                       to create or update Visitor.
     * @return VisitorDto.
     */
    @PutMapping()
    public ResponseEntity<VisitorDTO> generateVisitor(@RequestBody VisitorRequest visitorRequest,
            @RequestParam(required = false) Long visitorId) {
        VisitorDTO visitorDTO = visitorService.saveOrUpdateVisitor(visitorRequest, visitorId);
        if (visitorDTO == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(visitorDTO);
    }

    /**
     * Deactivate visitor by docNumber.
     *
     * @param visitorId The identifier of the visitor.
     * @return VisitorDTO.
     */
    @DeleteMapping("/{visitorId}")
    public ResponseEntity<VisitorDTO> deleteVisitor(@PathVariable Long visitorId) {
        return ResponseEntity.ok(visitorService.deleteVisitor(visitorId));
    }

    /**
     * Retrieves a specific visitor by their ID.
     * 
     * @param visitorId unique identifier of the visitor
     * @return VisitorDTO
     */
    @GetMapping("/{visitorId}")
    public ResponseEntity<VisitorDTO> getVisitorById(@PathVariable Long visitorId) {
        return ResponseEntity.ok(visitorService.getVisitorById(visitorId));
    }

}
