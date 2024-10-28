package ar.edu.utn.frc.tup.lc.iv.services.imp;

import java.time.LocalDateTime;
import java.util.*;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IVisitorService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.NoArgsConstructor;

/**
 * Service implementation for handling operations related to Authorized
 * entities.
 * This service converts Authorized entities to AuthorizedDTOs.
 */
@NoArgsConstructor
@Service
public class VisitorService implements IVisitorService {
    /**
     * Repository to access Authorized entities from the database.
     */
    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private AuthRepository authRepository;

    /**
     * ModelMapper for converting between entities and DTOs.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves all authorized entities from the repository and maps them to
     * a list of {@link VisitorDTO}.
     *
     * @param page the number of the page to retrieve.
     * @param size the number of records per page.
     * @return a list of {@link VisitorDTO} representing the authorized entities.
     */
    @Override
    public PaginatedResponse<VisitorDTO> getAllVisitors(int page, int size, String name, String lastName, String filter) {
        return null;
        /* Pageable pageable = PageRequest.of(page, size,
                Sort.by("lastName").and(Sort.by("name")));

        Page<VisitorEntity> visitorPage = visitorRepository.findAllByActive(true, pageable);

        // Convertimos el Page en una lista de VisitorDTO
        List<VisitorDTO> visitorDTOs = visitorPage.stream()
                .map(entity -> modelMapper.map(entity, VisitorDTO.class))
                .filter(visitorDTO -> {
                    if (Objects.nonNull(name) && !visitorDTO.getName().toLowerCase().contains(name.toLowerCase())) {
                        return false;
                    }
                    if (Objects.nonNull(lastName) && !visitorDTO.getLastName().toLowerCase().contains(lastName.toLowerCase())) {
                        return false;
                    }
                    if (Objects.nonNull(filter) && !visitorDTO.getName().toLowerCase().contains(filter.toLowerCase())
                            && !visitorDTO.getLastName().toLowerCase().contains(filter.toLowerCase())
                            && !visitorDTO.getDocNumber().toString().contains(filter)) {
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        return new PaginatedResponse<>(visitorDTOs, visitorPage.getTotalElements());*/
    }

    @Override
    public List<VisitorDTO> getAllVisitors() {
        Map<Long, VisitorDTO> visitors = new HashMap<>();

        List<AuthEntity> auths = authRepository.findAll();
        for (AuthEntity auth : auths) {
            if (visitors.containsKey(auth.getVisitor().getVisitorId())) {
                VisitorDTO visitor = visitors.get(auth.getVisitor().getVisitorId());
                if (!visitor.getVisitorTypes().contains(auth.getVisitorType())){
                    visitor.getVisitorTypes().add(auth.getVisitorType());
                    visitors.put(auth.getVisitor().getVisitorId(), visitor);
                }

            } else {
                VisitorDTO visitor = modelMapper.map(auth.getVisitor(), VisitorDTO.class);
                List<VisitorType> types = new ArrayList<>();
                types.add(auth.getVisitorType());
                visitor.setVisitorTypes(types);
                visitors.put(auth.getVisitor().getVisitorId(), visitor);
            }
        }
        List<VisitorEntity> visitorEntities = visitorRepository.findAll();
        for (VisitorEntity visitorEntity : visitorEntities) {
            VisitorDTO visitor = modelMapper.map(visitorEntity, VisitorDTO.class);
            visitors.put(visitorEntity.getVisitorId(), visitor);
        }
        return new ArrayList<>(visitors.values());

    }

    /**
     * Creates or updates a visitor.
     *
     * @param visitorRequest request DTO with visitor details.
     * @return the VisitorDTO with the authorization details.
     */
    @Override
    public VisitorDTO saveOrUpdateVisitor(VisitorRequest visitorRequest, Long visitorId) {
        VisitorEntity existVisitorEntitySaved = visitorRepository.findByDocNumber(visitorRequest.getDocNumber());
        if(existVisitorEntitySaved != null) {
            return null;
        }

        VisitorEntity existVisitorEntity = new VisitorEntity();

        if (visitorId != null) {
            existVisitorEntity = visitorRepository.findById(visitorId).orElse(null);
        }

        VisitorEntity visitorEntity;
        if (Objects.nonNull(existVisitorEntity)) {
            visitorEntity = existVisitorEntity;
        } else {
            visitorEntity = new VisitorEntity();
            visitorEntity.setCreatedDate(LocalDateTime.now());
        }

        visitorEntity.setActive(true);
        visitorEntity.setName(visitorRequest.getName());
        visitorEntity.setLastName(visitorRequest.getLastName());
        visitorEntity.setDocNumber(visitorRequest.getDocNumber());
        visitorEntity.setBirthDate(visitorRequest.getBirthDate());
        visitorEntity.setActive(visitorRequest.isActive());
        visitorEntity.setDocumentType(visitorRequest.getDocumentType());
        visitorEntity.setLastUpdatedDate(LocalDateTime.now());
        return modelMapper.map(visitorRepository.save(visitorEntity), VisitorDTO.class);
    }

    /**
     * fetch visitor by docNumber.
     *
     * @param docNumber document number of the visitor.
     * @return VisitorDTO.
     */
    @Override
    public VisitorDTO getVisitorByDocNumber(Long docNumber) {
        VisitorEntity visitorEntity = visitorRepository.findByDocNumber(docNumber);

        if (Objects.isNull(visitorEntity)) {
            return null;
        } else {
            return modelMapper.map(visitorEntity, VisitorDTO.class);
        }
    }

    /**
     * Deactivate visitor by docNumber.
     *
     * @param visitorId document number of the visitor.
     * @return VisitorDTO.
     */
    @Override
    public VisitorDTO deleteVisitor(Long visitorId) {
        Optional<VisitorEntity> visitorEntity = visitorRepository.findById(visitorId);

        if (visitorEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe el visitante con el id " + visitorId);
        }
        visitorEntity.get().setActive(false);
        visitorEntity.get().setLastUpdatedDate(LocalDateTime.now());

        return modelMapper.map(visitorRepository.save(visitorEntity.get()), VisitorDTO.class);
    }

    /**
     * fetch visitor by id.
     *
     * @param id unique identifier of the visitor
     * @return visitorDto with the given id
     */
    @Override
    public VisitorDTO getVisitorById(Long id) {
        Optional<VisitorEntity> visitorEntity = visitorRepository.findById(id);
        if (visitorEntity.isEmpty()) {
            throw new EntityNotFoundException("No existe el visitante con el id " + id);
        }
        return modelMapper.map(visitorEntity.get(), VisitorDTO.class);
    }

}
