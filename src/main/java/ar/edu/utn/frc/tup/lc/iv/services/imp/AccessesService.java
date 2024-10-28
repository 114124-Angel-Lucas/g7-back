package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.repositories.AccessesRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IAccessesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for handling operations
 * related to Authorized entities.
 */
@Service
public class AccessesService implements IAccessesService {

    /**
     * repository of authorizations.
     */
    @Autowired
    private AccessesRepository accessesRepository;

    /**
     * ModelMapper for converting between entities and DTOs.
     */
    @Autowired
    private ModelMapper modelMapper;


    /**
     * @return
     */
    @Override
    public List<AccessDTO> getAllAccess() {
        return accessesRepository.findAll().stream()
                .map(accessEntity -> {
                    AccessDTO accessDTO = modelMapper.map(accessEntity, AccessDTO.class);

                    accessDTO.setAuthorizerId(accessEntity.getAuth().getCreatedUser());
                    accessDTO.setDocType(accessEntity.getAuth().getVisitor().getDocumentType());
                    accessDTO.setName(accessEntity.getAuth().getVisitor().getName());
                    accessDTO.setLastName(accessEntity.getAuth().getVisitor().getLastName());
                    accessDTO.setDocNumber(accessEntity.getAuth().getVisitor().getDocNumber());
                    accessDTO.setVisitorType(accessEntity.getAuth().getVisitorType());
                    return accessDTO;
                }).sorted(Comparator.comparing(AccessDTO::getActionDate).reversed())
                .collect(Collectors.toList());
    }

    /**
     * @return
     */
    @Override
    public List<AccessDTO> getAllEntries() {
        return accessesRepository.findByAction(ActionTypes.ENTRY).stream()
                .map(accessEntity -> modelMapper.map(accessEntity, AccessDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * @return
     */
    @Override
    public List<AccessDTO> getAllExits() {
        return accessesRepository.findByAction(ActionTypes.EXIT).stream()
                .map(accessEntity -> modelMapper.map(accessEntity, AccessDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * @return
     */
    @Override
    public List<AccessDTO> getAllAccessByType(VisitorType visitorType) {
        return accessesRepository.findByAuth_VisitorType(visitorType).stream()
                .map(accessEntity -> modelMapper.map(accessEntity, AccessDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * @return
     */
    @Override
    public List<AccessDTO> getAllAccessByTypeAndExternalID(VisitorType visitorType, Long externalId) {
        return accessesRepository.findByAuth_VisitorTypeAndAuth_ExternalID(visitorType,externalId).stream()
                .map(accessEntity -> modelMapper.map(accessEntity, AccessDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * @return
     */
    @Override
    public Boolean canDoAction(String carPlate, ActionTypes action) {
        AccessEntity acc = accessesRepository.findByVehicleReg(carPlate).stream()
                .max(Comparator.comparing(AccessEntity::getActionDate))
                .orElse(null);
        if (acc == null) {return true;}
        return !acc.getAction().equals(action);
    }

    /**
     * @param accessEntity
     * @return
     */
    @Override
    public AccessDTO registerAccess(AccessEntity accessEntity) {
        return modelMapper.map(accessesRepository.save(accessEntity),AccessDTO.class);
    }
}
