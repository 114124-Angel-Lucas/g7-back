package ar.edu.utn.frc.tup.lc.iv.services;

import java.io.IOException;

/**
 * Interface for a QR Service.
 */
public interface IQRService {
    /**
     * Generates a QR code with the information of a specific visitor.
     * @param docNumber Document number of the visitor
     * for whom the QR code should be generated.
     * @return A byte array representing the QR code image in PNG format.
     * @throws IOException If there is an issue while generating the QR code.
     */
    byte[] generateQrForVisitor(Long docNumber) throws IOException;
}
