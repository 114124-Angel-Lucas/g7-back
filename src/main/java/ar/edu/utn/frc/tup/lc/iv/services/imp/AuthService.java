package ar.edu.utn.frc.tup.lc.iv.services.imp;

import java.time.LocalDateTime;
import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.AuthRange;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import jakarta.transaction.Transactional;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRangeRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IAuthService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for handling operations
 * related to Authorized entities.
 */
@Service
public class AuthService implements IAuthService {

    /**
     * repository of authorizations.
     */
    @Autowired
    private AuthRepository authRepository;


    /**
     * repository of visitors.
     */
    @Autowired
    private VisitorRepository visitorRepository;

    /**
     * service of visitor.
     */
    @Autowired
    private VisitorService visitorService;

    /**
     * service of accesses.
     */
    @Autowired
    private AccessesService accessesService;

    /**
     * service of auth ranges.
     */
    @Autowired
    private AuthRangeService authRangeService;

    /**
     * ModelMapper for converting between entities and DTOs.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get all authorizations.
     *
     * @return List<AuthDTO>
     */
    @Override
    public List<AuthDTO> getAllAuths() {
        List<AuthEntity> authEntities = authRepository.findAll()
                .stream().filter(AuthEntity::isActive).toList();
        List<AuthDTO> authDTOs = new ArrayList<>();

        for (AuthEntity authEntity : authEntities) {
            VisitorEntity visitorEntity = authEntity.getVisitor();

            AuthDTO authDTO = modelMapper.map(authEntity, AuthDTO.class);
            VisitorDTO visitorDTO = modelMapper.map(visitorEntity, VisitorDTO.class);

            List<AuthRangeDTO> authRangeDTOs = authRangeService.getAuthRangesByAuth(authEntity);

            authDTO.setAuthorizerId(authEntity.getCreatedUser());
            authDTO.setVisitor(visitorDTO);
            authDTO.setAuthRanges(authRangeDTOs);

            authDTOs.add(authDTO);
        }

        return authDTOs;
    }

    /**
     * Retrieves a list of individual authorizations
     * by document number.
     *
     * @param docNumber document number.
     * @return list of authorized persons.
     */
    @Override
    public List<AuthDTO> getAuthsByDocNumber(Long docNumber) {
        VisitorEntity visitorEntity = visitorRepository.findByDocNumber(docNumber);
        List<AuthEntity> authEntities = authRepository.findByVisitor(visitorEntity);
        List<AuthDTO> authDTOs = new ArrayList<>();

        for (AuthEntity authEntity : authEntities) {
            AuthDTO authDTO = modelMapper.map(authEntity, AuthDTO.class);
            List<AuthRangeDTO> authRangeDTOs = authRangeService.getAuthRangesByAuth(authEntity);
            authDTO.setAuthRanges(authRangeDTOs);
            authDTOs.add(authDTO);
        }

        return authDTOs;
    }

    /**
     * Retrieves a list of individual authorizations
     * by document number.
     *
     * @param visitorType document number.
     * @return list of authorized persons.
     */
    @Override
    public List<AuthDTO> getAuthsByType(VisitorType visitorType) {
        List<AuthEntity> authEntities = authRepository.findByVisitorType(visitorType);
        List<AuthDTO> authDTOs = new ArrayList<>();

        for (AuthEntity authEntity : authEntities) {
            AuthDTO authDTO = modelMapper.map(authEntity, AuthDTO.class);
            List<AuthRangeDTO> authRangeDTOs = authRangeService.getAuthRangesByAuth(authEntity);
            authDTO.setAuthRanges(authRangeDTOs);
            authDTOs.add(authDTO);
        }

        return authDTOs;
    }

    @Override
    public List<AuthDTO> getAuthsByTypeAndExternalId(VisitorType visitorType, Long externalID) {
        return null;
    }


    /**
     * Authorize visitor with authorized ranges.
     *
     * @param visitorAuthRequest request.
     * @return authorization created.
     */

    @Override
    @Transactional
    public AuthDTO createAuthorization(VisitorAuthRequest visitorAuthRequest, Long creatorID) {
        visitorAuthRequest.getVisitorRequest().setActive(true);
        VisitorDTO visitorDTO;

        // verifica si ya existe el visitante en la base de datos
        VisitorDTO visitorDTOAlreadyExist = visitorService
                .getVisitorByDocNumber(visitorAuthRequest.getVisitorRequest().getDocNumber());

        if (visitorDTOAlreadyExist != null) {
            visitorDTO = visitorDTOAlreadyExist;
        } else {
            visitorDTO = visitorService.saveOrUpdateVisitor(visitorAuthRequest.getVisitorRequest(), null);
        }

        //TODO Si ya tiene auth validar que no se repita ahi se tiene que modificar

        return createNewAuthorization(visitorDTO, visitorAuthRequest, creatorID);
    }

    @Override
    public AccessDTO authorizeVisitor(AccessDTO accessDTO, Long guardID) {
        List<AuthDTO> authDTOs = getValidAuthsByDocNumber(accessDTO.getDocNumber());

        if (authDTOs.size() < 1) {
            return null;
        }

        AuthEntity authEntity = authRepository.getReferenceById(authDTOs.get(0).getAuthId());

        AccessEntity accessEntity = new AccessEntity(
                guardID, guardID, authEntity, accessDTO.getAction(), LocalDateTime.now(), accessDTO.getVehicleType(),
                accessDTO.getVehicleReg(), accessDTO.getVehicleDescription(), authDTOs.get(0).getPlotId(),
                authDTOs.get(0).getExternalID(), accessDTO.getComments());

        return accessesService.registerAccess(accessEntity);
    }

    /**
     * Retrieves a list of valid authorizations
     * by document number.
     *
     * @param docNumber document number.
     * @return list of valid authorizations.
     */
    @Override
    public List<AuthDTO> getValidAuthsByDocNumber(Long docNumber) {
        List<AuthDTO> dtos = getAuthsByDocNumber(docNumber);
        List<AuthDTO> validAuths = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        for (AuthDTO authDTO : dtos) {
            if (authDTO.isActive()) {
                List<AuthRangeDTO> validAuthRanges = authRangeService.getValidAuthRanges(authDTO.getAuthRanges(), currentDate,
                        currentTime);
                if (!validAuthRanges.isEmpty()) {
                    authDTO.setAuthRanges(validAuthRanges);
                    validAuths.add(authDTO);
                }
            }
        }

        return validAuths;
    }

    @Override
    public Boolean isAuthorized(Long documentNumber) {
        return getValidAuthsByDocNumber(documentNumber).size() > 0;
    }


    /**
     * Create a new authorization.
     *
     * @param visitorDTO         visitor
     * @param visitorAuthRequest request
     * @return new authorization
     */
    protected AuthDTO createNewAuthorization(VisitorDTO visitorDTO, VisitorAuthRequest visitorAuthRequest, Long creatorID) {
        AuthEntity authEntity = new AuthEntity(creatorID, creatorID);
        authEntity.setVisitor(modelMapper.map(visitorDTO, VisitorEntity.class));
        authEntity.setVisitorType(visitorAuthRequest.getVisitorType());
        authEntity.setActive(true);
        authEntity.setPlotId(visitorAuthRequest.getPlotId());
        authEntity.setExternalID(visitorAuthRequest.getExternalID());
        authEntity = authRepository.save(authEntity);

        AuthDTO authDTO = new AuthDTO();
        authDTO.setPlotId(authEntity.getPlotId());
        authDTO.setAuthId(authEntity.getAuthId());
        authDTO.setVisitorType(authEntity.getVisitorType());
        authDTO.setVisitor(modelMapper.map(authEntity.getVisitor(), VisitorDTO.class));
        authDTO.setActive(authEntity.isActive());


        if (visitorAuthRequest.getVisitorType() == VisitorType.PROVIDER || visitorAuthRequest.getVisitorType() == VisitorType.WORKER) {
            authDTO.setAuthRanges(authRangeService.getAuthRangesByAuthExternalID(visitorAuthRequest.getExternalID()));
        } else {
            List<AuthRange> authorizedRangesList = authRangeService.registerAuthRanges(visitorAuthRequest.getAuthRangeRequest(),
                    authEntity, visitorDTO);
            authDTO.setAuthRanges(authorizedRangesList.stream()
                    .filter(Objects::nonNull)
                    .map(auth -> modelMapper.map(auth, AuthRangeDTO.class))
                    .collect(Collectors.toList()));
        }

        return authDTO;
    }

    private List<AuthRangeDTO> getAuthorizedRangesList(Long providerID) {
        return authRangeService.getAuthRangesByAuth(authRepository.findByVisitorTypeAndExternalID(VisitorType.PROVIDER_ORGANIZATION, providerID).get(0));
    }

    /**
     * update authorization list with new authorized ranges.
     *
     * @param existingAuth       existing authorization
     * @param visitorDTO         visitor
     * @param visitorAuthRequest request
     * @return updated authorization
     */
    @Override
    public AuthDTO updateAuthorization(AuthDTO existingAuth, VisitorDTO visitorDTO,
                                       VisitorAuthRequest visitorAuthRequest) {
        // Registra los nuevos rangos de autorización
        List<AuthRange> newAuthorizedRanges = authRangeService.registerAuthRanges(visitorAuthRequest.getAuthRangeRequest(),
                modelMapper.map(existingAuth, AuthEntity.class), visitorDTO);

        for (AuthRange range : newAuthorizedRanges) {
            AuthRangeDTO authRangeDTO = new AuthRangeDTO();

            authRangeDTO.setAuthRangeId(range.getAuthRangeId());
            authRangeDTO.setActive(range.isActive());
            authRangeDTO.setDaysOfWeek(range.getDaysOfWeek());
            authRangeDTO.setDateFrom(range.getDateFrom());
            authRangeDTO.setDateTo(range.getDateTo());
            authRangeDTO.setHourFrom(range.getHourFrom());
            authRangeDTO.setHourTo(range.getHourTo());

            existingAuth.getAuthRanges().add(authRangeDTO);
        }

        return existingAuth; // Devuelve la autorización actualizada
    }

}
