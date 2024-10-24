package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QRServiceTest {

    @Mock
    private VisitorRepository visitorRepository;

    @Spy
    @InjectMocks
    private QRService qrService;

    private VisitorEntity visitor;

    @BeforeEach
    public void setUp() {
        visitor = new VisitorEntity();
        visitor.setName("Juan");
        visitor.setLastName("Pérez");
        visitor.setDocNumber(12345678L);
    }

    @Test
    public void testGenerateQrForVisitor_Success() throws IOException {
        when(visitorRepository.findByDocNumber(anyLong())).thenReturn(visitor);

        byte[] qrCode = qrService.generateQrForVisitor(12345678L);

        assertNotNull(qrCode);
        assertTrue(qrCode.length > 0);
    }

    @Test
    public void testGenerateQrForVisitor_VisitorNotFound() {
        when(visitorRepository.findByDocNumber(anyLong())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            qrService.generateQrForVisitor(12345678L);
        });

        assertEquals("No se encontró un visitante con el número de documento proporcionado.", exception.getMessage());
    }

}
