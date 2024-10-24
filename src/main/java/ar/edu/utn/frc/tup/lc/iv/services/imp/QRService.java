package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IQRService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Implementation of the QR code generation service for visitors.
 * This class uses the ZXing library to generate
 * QR codes based on the information of a
 * visitor stored in the database.
 */
@Service
@NoArgsConstructor
public class QRService implements IQRService {
    /**
     * Visitor Repository to inject dependency.
     */
    @Autowired
    private VisitorRepository visitorsRepository;

    /**
     * Constant for the width and height of a QR code.
     */
    private static final int WH = 150;

    /**
     * Generates a QR code with the information of a specific visitor.
     * @param docNumber Document number of the visitor
     * for whom the QR code should be generated.
     * @return A byte array representing the QR code image in PNG format.
     * @throws IOException If there is an issue while generating the QR code.
     */
    @Override
    public byte[] generateQrForVisitor(Long docNumber) throws IOException {
        VisitorEntity visitor = visitorsRepository.findByDocNumber(docNumber);

        if (visitor == null) {
            throw new IllegalArgumentException("No se encontró un visitante con el número de documento proporcionado.");
        }

        String qrData = "Name: " + visitor.getName() + ", "
                + "Last Name: " + visitor.getLastName() + ", "
                + "Document: " + visitor.getDocNumber();

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, WH, WH);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        } catch (WriterException e) {
            throw new IOException("Error generating QR code.", e); // Preserve the stack trace
        }
    }
}
