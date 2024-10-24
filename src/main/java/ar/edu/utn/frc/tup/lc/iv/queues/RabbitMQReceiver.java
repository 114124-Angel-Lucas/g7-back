package ar.edu.utn.frc.tup.lc.iv.queues;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.utn.frc.tup.lc.iv.configs.RabbitMQConfig;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthService;

/**
 * Service class for receiving messages from RabbitMQ.
 * This class listens to messages from the configured queue and processes them.
 */
@Service
public class RabbitMQReceiver {

    /**
     * The {@link AuthService} is used to handle the visitor authorization process.
     * It is injected via Spring's {@link Autowired} annotation.
     */
    @Autowired
    private AuthService authService;

    /**
     * The {@link ObjectMapper} is used for converting Java objects to JSON.
     * It is injected via field injection.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Listens for messages from the RabbitMQ queue and processes them.
     * The incoming message is deserialized into a {@link VisitorAuthRequest}
     * object,
     * and the visitor authorization process is triggered.
     *
     * @param jsonMessage the incoming message in JSON format.
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String jsonMessage) {
        try {
            VisitorAuthRequest visitorAuthRequest = objectMapper.readValue(jsonMessage, VisitorAuthRequest.class);
            authService.createAuthorization(visitorAuthRequest, 0L);
        } catch (JsonProcessingException e) {
            return;
        }
    }
}
