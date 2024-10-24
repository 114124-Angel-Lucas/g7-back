package ar.edu.utn.frc.tup.lc.iv.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.queues.RabbitMQSender;

/**
 * Controller for handling requests related to RabbitMQ messaging.
 * This controller allows sending visitor authorization requests to RabbitMQ.
 */
@RestController
public class RabbitMQController {

    /**
     * The {@link RabbitMQSender} is responsible for sending messages to RabbitMQ.
     * It is injected via constructor injection and is used to send visitor
     * authorization requests to the configured RabbitMQ exchange.
     */
    private final RabbitMQSender rabbitMQSenderService; // Renamed field

    /**
     * Constructor to inject the RabbitMQ sender service.
     *
     * @param rabbitMQSender the service used to send messages to RabbitMQ.
     */
    public RabbitMQController(RabbitMQSender rabbitMQSender) {
        this.rabbitMQSenderService = rabbitMQSender; // Update to use the renamed field
    }

    /**
     * Endpoint to send a visitor authorization request to RabbitMQ.
     *
     * @param visitorAuthRequest the visitor authorization request payload.
     * @return a {@link ResponseEntity} with a success message upon successfully
     *         sending the message.
     *         @PostMapping("/send")
     *         public ResponseEntity<String> send(@RequestBody VisitorAuthRequest
     *         visitorAuthRequest) {
     *         rabbitMQSenderService.sendMessage(visitorAuthRequest); // Update to
     *         use the renamed field
     *         return ResponseEntity.ok("Visitante enviado con éxito!");
     *         }
     */
}
