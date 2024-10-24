package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.services.IQRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;

/**
 * Controller for handling QR code-related requests.
 * This class exposes a REST API endpoint to generate QR codes
 * for visitors based on their document number.
 */
@RestController
@RequestMapping("/qr")
public class QRController {
    /**
     * QR Service dependency injection.
     */
    @Autowired
    private IQRService qrService;
    /**
     * Const for a APIERROR 500.
     */
    private static final int NUMBER = 500;
    /**
     * Endpoint to generate a QR code for a visitor based on their document number.
     * @param docNumber The document number of the visitor
     * for whom the QR code should be generated.
     * @return A ResponseEntity containing the QR code image in byte format,
     * or an error response in case of failure.
     */
    @GetMapping("/{docNumber}")
    public ResponseEntity<byte[]> generateQr(@PathVariable Long docNumber) {
        try {
            byte[] qrImage = qrService.generateQrForVisitor(docNumber);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrImage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            return ResponseEntity.status(NUMBER).body(null);
        }
    }
}
