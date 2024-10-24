package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.services.IQRService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QRController.class)
public class QRControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IQRService qrService;

    @Test
    void generateQrTest() throws Exception {

        Long docNumber = 12345678L;
        byte[] qrCode = new byte[]{};

        when(qrService.generateQrForVisitor(docNumber)).thenReturn(qrCode);

        mockMvc.perform(get("/qr/{docNumber}", docNumber))
                .andExpect(status().isOk());
    }

    @Test
    void generateQrErrorTest() throws Exception {

        Long docNumber = 12345678L;
        
        when(qrService.generateQrForVisitor(docNumber)).thenThrow(new IOException("Error generating QR code"));

        mockMvc.perform(get("/qr/{docNumber}", docNumber))
                .andExpect(status().isInternalServerError());
    }
}
