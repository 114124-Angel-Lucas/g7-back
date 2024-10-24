package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.AuthRange;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRangeRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthServiceTest {

    @MockBean
    private VisitorService visitorService;

    @MockBean
    private AuthRepository authRepository;

    @MockBean
    private AuthRangeRepository authRangeRepository;

    @MockBean
    private VisitorRepository visitorRepository;

    @SpyBean
    private AuthService authService;


    @Autowired
    ModelMapper modelMapper;
    private VisitorEntity visitorEntity;
    private AuthEntity authEntity;

    private AuthRangeEntity authRangeEntity;

    private List<AuthEntity> authEntities;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        visitorEntity = new VisitorEntity();
        visitorEntity.setDocNumber(123456L);

        authEntity = new AuthEntity();
        authEntity.setAuthId(1L);
        authEntity.setVisitor(visitorEntity);

        authRangeEntity = new AuthRangeEntity();
        authRangeEntity.setAuthRangeId(1L);
        authRangeEntity.setAuthId(authEntity);

        authEntities = new ArrayList<>();
        authEntities.add(authEntity);


    }
    @Test
    void getAuthsByDocNumberTest(){
        Long docNumber = 123456L;

        // Mocking the repository calls
        when(visitorRepository.findByDocNumber(docNumber)).thenReturn(visitorEntity);
        when(authRepository.findByVisitor(visitorEntity)).thenReturn(authEntities);
        when(authRangeRepository.findByAuthId(authEntity)).thenReturn(Collections.singletonList(authRangeEntity));

        List<AuthDTO> result = authService.getAuthsByDocNumber(docNumber);

        assertNotNull(result);
        assertEquals(1, result.size());

        AuthDTO authDTO = result.get(0);
        assertEquals(authEntity.getAuthId(), authDTO.getAuthId());

        assertNotNull(authDTO.getAuthRanges());
        assertEquals(1, authDTO.getAuthRanges().size());

        verify(visitorRepository).findByDocNumber(docNumber);
        verify(authRepository).findByVisitor(visitorEntity);
        verify(authRangeRepository).findByAuthId(authEntity);
    }

    //@Test
    void authorizeVisitorTest(){

        VisitorRequest visitorRequest =
                new VisitorRequest("Joaquin","Zabala", DocumentType.DNI,123456L,LocalDate.of(2005,3,17),true);

        VisitorDTO visitorDTO = modelMapper.map(visitorRequest, VisitorDTO.class);
        visitorDTO.setVisitorId(1L);

        AuthRangeRequestDTO authRangeRequest = new AuthRangeRequestDTO();
        authRangeRequest.setHourFrom(LocalTime.of(10 , 0));
        authRangeRequest.setHourTo(LocalTime.of(20 , 0));
        authRangeRequest.setDaysOfWeek(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        authRangeRequest.setDateFrom(LocalDate.of(2024, 1, 1));
        authRangeRequest.setDateTo(LocalDate.of(2024, 1, 31));
        authRangeRequest.setActive(true);

        List<AuthRangeRequestDTO> authRangeRequestList = new ArrayList<>();
        authRangeRequestList.add(authRangeRequest);

        VisitorAuthRequest visitorAuthRequest = new VisitorAuthRequest();
        visitorAuthRequest.setVisitorType(VisitorType.OWNER);
        visitorAuthRequest.setVisitorRequest(visitorRequest);
        visitorAuthRequest.setAuthRangeRequest(authRangeRequestList);

        //para evitar nulos en los metodos privados
        AuthEntity authEntity1 = new AuthEntity(1L, modelMapper.map(visitorDTO, VisitorEntity.class), VisitorType.OWNER,true);
        when(authRepository.save(any(AuthEntity.class))).thenReturn(authEntity1);

        AuthRange authorizedRanges = modelMapper.map(authRangeRequest, AuthRange.class);
        when(authService.registerAuthorizedRange(any(RegisterAuthorizationRangesDTO.class)))
                .thenReturn(authorizedRanges);

        when(visitorService.saveOrUpdateVisitor(visitorRequest , null)).thenReturn(visitorDTO);

        AuthDTO result = authService.createAuthorization(visitorAuthRequest);

        assertNotNull(result);
        assertEquals(result.getVisitor() , visitorDTO);
        assertEquals(result.getAuthRanges().get(0).getDateFrom(), authRangeRequest.getDateFrom());

    }

    //@Test
    void authorizeVisitorWhenAuthorizationExists() throws Exception {
        // Crear la solicitud de visitante
        VisitorRequest visitorRequest =
                new VisitorRequest("Joaquin", "Zabala", DocumentType.DNI, 123456L,
                        LocalDate.of(2005, 3, 17), true);

        VisitorDTO visitorDTO = modelMapper.map(visitorRequest, VisitorDTO.class);
        visitorDTO.setVisitorId(1L);

        // Crear el rango de autorización
        AuthRangeRequestDTO authRangeRequest = new AuthRangeRequestDTO();
        authRangeRequest.setHourFrom(LocalTime.of(10, 0));
        authRangeRequest.setHourTo(LocalTime.of(20, 0));
        authRangeRequest.setDaysOfWeek(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        authRangeRequest.setDateFrom(LocalDate.of(2024, 1, 1));
        authRangeRequest.setDateTo(LocalDate.of(2024, 1, 31));

        // Crear la solicitud de autorización del visitante
        VisitorAuthRequest visitorAuthRequest = new VisitorAuthRequest();
        visitorAuthRequest.setVisitorType(VisitorType.OWNER);
        visitorAuthRequest.setVisitorRequest(visitorRequest);
        visitorAuthRequest.setAuthRangeRequest(List.of(authRangeRequest));

        // Crear un VisitorEntity mock
        VisitorEntity visitorEntity = modelMapper.map(visitorDTO, VisitorEntity.class);
        visitorEntity.setVisitorId(1L);

        // se crean entidades existentes
        AuthEntity existingAuthEntity = new AuthEntity(1L, visitorEntity, VisitorType.OWNER, true);
        List<AuthEntity> authEntities = Collections.singletonList(existingAuthEntity);

        AuthRangeDTO authRangeExist = new AuthRangeDTO();
        authRangeExist.setHourFrom(LocalTime.of(10, 0));
        authRangeExist.setHourTo(LocalTime.of(20, 0));
        authRangeExist.setDaysOfWeek(List.of(DayOfWeek.MONDAY));
        authRangeExist.setDateFrom(LocalDate.of(2024, 10, 1));
        authRangeExist.setDateTo(LocalDate.of(2024, 11, 10));


        authRangeEntity.setHourFrom(LocalTime.of(10, 0));
        authRangeEntity.setHourTo(LocalTime.of(20, 0));
        authRangeEntity.setDaysOfWeek("MONDAY");
        authRangeEntity.setDateFrom(LocalDate.of(2024, 10, 1));
        authRangeEntity.setDateTo(LocalDate.of(2024, 11, 10));

        List<AuthRangeEntity> authRangeEntities = new ArrayList<>();
        authRangeEntities.add(authRangeEntity);

        AuthRange authorizedRanges = modelMapper.map(authRangeExist, AuthRange.class);
        authorizedRanges.setAuthRangeId(1L);

        // Mockear los repositorios de todos los metodos que se van a utilizar
        when(visitorRepository.findByDocNumber(visitorRequest.getDocNumber())).thenReturn(visitorEntity);
        when(authRepository.findByVisitor(visitorEntity)).thenReturn(authEntities);
        when(authRangeRepository.findByAuthId(existingAuthEntity)).thenReturn(authRangeEntities);
        when(visitorService.saveOrUpdateVisitor(visitorRequest , null)).thenReturn(visitorDTO);
        when(authService.registerAuthorizedRange(any(RegisterAuthorizationRangesDTO.class))).thenReturn(authorizedRanges);

        AuthDTO result = authService.createAuthorization(visitorAuthRequest);

        assertNotNull(result);
        assertEquals(result.getVisitor() , visitorDTO);
        assertEquals(result.getAuthRanges().size(), 2);
    }
    @Test
    void getValidAuthsByDocNumber(){
        AuthDTO authDTO = new AuthDTO();
        authDTO.setActive(true);
        authDTO.setVisitorType(VisitorType.OWNER);
        authDTO.setVisitor(new VisitorDTO());
        authDTO.setAuthId(1L);

        AuthRangeDTO authRangeDTO = new AuthRangeDTO();
        authRangeDTO.setDaysOfWeek(List.of(DayOfWeek.SUNDAY));
        authRangeDTO.setHourFrom(LocalTime.of(10, 0));
        authRangeDTO.setHourTo(LocalTime.of(20, 0));
        authRangeDTO.setDateFrom(LocalDate.of(2024, 10, 1));
        authRangeDTO.setDateTo(LocalDate.of(2025, 11, 10));
        authRangeDTO.setActive(true);

        authDTO.setAuthRanges(Collections.singletonList(authRangeDTO));

        when(authService.getAuthsByDocNumber(1L)).thenReturn(Collections.singletonList(authDTO));
        List<AuthDTO> result = authService.getValidAuthsByDocNumber(1L);

        //assertEquals(result.size(), 1);
    }
    @Test
    public void findExistingAuthorizationTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        VisitorAuthRequest visitorAuthRequest = new VisitorAuthRequest();
        visitorAuthRequest.setVisitorType(VisitorType.OWNER);

        VisitorRequest visitorRequest = new VisitorRequest();
        visitorRequest.setDocNumber(1L);
        visitorAuthRequest.setVisitorRequest(visitorRequest);

        List<AuthDTO> authDTOList = new ArrayList<>();
        AuthDTO authDTO = new AuthDTO();
        authDTO.setVisitorType(VisitorType.OWNER);
        authDTO.setAuthRanges(new ArrayList<>());
        authDTO.setVisitor(new VisitorDTO());
        authDTOList.add(authDTO);

        when(authService.getAuthsByDocNumber(1L)).thenReturn(authDTOList);

        Method method = AuthService.class.getDeclaredMethod("findExistingAuthorization", VisitorAuthRequest.class);
        method.setAccessible(true);
        Optional<AuthDTO> result = (Optional<AuthDTO>) method.invoke(authService, visitorAuthRequest);

        // Verificar los resultados
        assertTrue(result.isPresent());
        assertEquals(authDTO, result.get());
    }

    //@Test
    void testCreateNewAuthorization() throws NoSuchMethodException {

        VisitorDTO visitorDTO = new VisitorDTO();
        visitorDTO.setLastName("Pipino");
        visitorDTO.setDocNumber(123456L);
        visitorDTO.setName("Maria");
        visitorDTO.setActive(true);
        visitorDTO.setDocNumber(123456L);
        visitorDTO.setVisitorId(1L);

        AuthRangeRequestDTO authRangeRequest = new AuthRangeRequestDTO();
        authRangeRequest.setDateFrom(LocalDate.of(2022, 1, 1));
        authRangeRequest.setDateTo(LocalDate.of(2022, 1, 31));
        authRangeRequest.setDaysOfWeek(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        authRangeRequest.setPlotId(1L);
        authRangeRequest.setActive(true);

        List<AuthRangeRequestDTO> authRangeRequests = new ArrayList<>();
        authRangeRequests.add(authRangeRequest);

        VisitorAuthRequest visitorAuthRequest = new VisitorAuthRequest();
        visitorAuthRequest.setVisitorType(VisitorType.OWNER);
        visitorAuthRequest.setVisitorRequest(modelMapper.map(visitorDTO, VisitorRequest.class));
        visitorAuthRequest.setAuthRangeRequest(authRangeRequests);
        visitorAuthRequest.setVisitorType(VisitorType.OWNER);

        authEntity = new AuthEntity();
        authEntity.setAuthId(1L);
        authEntity.setVisitor(modelMapper.map(visitorDTO, VisitorEntity.class));
        authEntity.setVisitorType(VisitorType.OWNER);
        authEntity.setActive(true);


        when(authRepository.save(any())).thenReturn(authEntity);

        AuthDTO result = authService.createNewAuthorization(visitorDTO, visitorAuthRequest);

        assertNotNull(result);
        assertEquals(result.getVisitorType() , VisitorType.OWNER);
        assertEquals(result.getVisitor() , visitorDTO);
    }
   @Test
    void updateAuthorizationTest(){

        AuthDTO authDTO = new AuthDTO();
        authDTO.setAuthId(1L);
        authDTO.setVisitorType(VisitorType.OWNER);
        authDTO.setVisitor(new VisitorDTO());
        authDTO.setAuthRanges(new ArrayList<>());


   }
}


