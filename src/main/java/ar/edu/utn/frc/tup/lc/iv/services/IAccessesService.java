package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;

import java.util.List;

/**
 * This interface defines the contract for a service
 * that manages authorized persons.
 */
public interface IAccessesService {

    /**
     * Retrieves a list of all authorized persons.
     *
     * @return A list of {@link AuthDTO} representing
     * all authorized persons.
     */
    List<AccessDTO> getAllAccess();
    List<AccessDTO> getAllEntries();
    List<AccessDTO> getAllExits();
    List<AccessDTO> getAllAccessByType(VisitorType visitorType);
    List<AccessDTO> getAllAccessByTypeAndExternalID(VisitorType visitorType, Long externalId);
    Boolean canDoAction(String carPlate, ActionTypes action);
    AccessDTO registerAccess(AccessEntity accessEntity);
}
