package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.services.IAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @MockBean
    private IAuthService authService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    AuthDTO authDTO;

    @BeforeEach
    void setUp() {
        //RESPONSE
        VisitorDTO visitorDTO = new VisitorDTO();
        visitorDTO.setDocNumber(123456L);
        visitorDTO.setName("Joaquin");
        visitorDTO.setLastName("Zabala");
        visitorDTO.setBirthDate(LocalDate.of(2005, 3, 17));
        visitorDTO.setActive(true);
        visitorDTO.setVisitorId(1L);

        AuthRangeDTO authRangeDTO = new AuthRangeDTO();
        authRangeDTO.setDateFrom(LocalDate.of(2024, 1, 1));
        authRangeDTO.setDateTo(LocalDate.of(2024, 1, 31));

        List<AuthRangeDTO> rangeDTOS = new ArrayList<>();
        rangeDTOS.add(authRangeDTO);

        authDTO = new AuthDTO();
        authDTO.setAuthId(1L);
        authDTO.setVisitor(visitorDTO);
        authDTO.setAuthRanges(rangeDTOS);
        authDTO.setVisitorType(VisitorType.VISITOR);
        authDTO.setActive(true);
    }
    @Test
    void getAuth() throws Exception {

        when(authService.getAuthsByDocNumber(123456L)).thenReturn(List.of(authDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/auths")
                        .param("docNumber", "123456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].auth_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].visitor.name").value("Joaquin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].auth_ranges.length()").value(1));
    }

    @Test
    void getValidAuths() throws Exception {

        when(authService.getValidAuthsByDocNumber(123456L)).thenReturn(List.of(authDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/auths/valids")
                        .param("docNumber", "123456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].auth_id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].visitor.name").value("Joaquin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].auth_ranges.length()").value(1));

    }

    @Test
    void authorizeVisitor() throws Exception {
        //REQUEST
        VisitorAuthRequest visitorAuthRequest = new VisitorAuthRequest();
        visitorAuthRequest.setVisitorType(VisitorType.OWNER);
        visitorAuthRequest.setVisitorRequest(new VisitorRequest("Joaquin","Zabala", DocumentType.DNI,123456L,LocalDate.of(2005,3,17),true));

        AuthRangeRequestDTO authRange= new AuthRangeRequestDTO();
        authRange.setDateFrom(LocalDate.of(2024, 1, 1));
        authRange.setDateTo(LocalDate.of(2024, 1, 31));

        List<AuthRangeRequestDTO> rangeRequest = new ArrayList<>();
        rangeRequest.add(authRange);

        visitorAuthRequest.setAuthRangeRequest(rangeRequest);



        when(authService.createAuthorization(any(VisitorAuthRequest.class))).thenReturn(authDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/auths")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitorAuthRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.auth_id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.visitor.doc_number").value(123456L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.auth_ranges[0].date_from").value("01-01-2024"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.auth_ranges[0].date_to").value("31-01-2024"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.visitor_type").value("VISITOR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.auth_ranges.length()").value(1));
    }
}