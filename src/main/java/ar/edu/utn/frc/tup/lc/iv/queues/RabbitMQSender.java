package ar.edu.utn.frc.tup.lc.iv.queues;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.utn.frc.tup.lc.iv.configs.RabbitMQConfig;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;

/**
 * Service class for sending messages to RabbitMQ.
 * This class converts a {@link VisitorAuthRequest} object to JSON format
 * and sends it to the configured RabbitMQ exchange and routing key.
 */
@Service
public class RabbitMQSender {

    /**
     * The {@link RabbitTemplate} is used for sending messages to RabbitMQ.
     * It is injected via constructor injection.
     */
    private final RabbitTemplate template; // Renamed field

    /**
     * The {@link ObjectMapper} is used to deserialize JSON messages into Java
     * objects.
     * It is injected via Spring's {@link Autowired} annotation.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Constructor for injecting the RabbitTemplate.
     *
     * @param rabbitTemplate the RabbitMQ template used for sending messages.
     */
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.template = rabbitTemplate; // Update the assignment
    }

    /**
     * Sends a {@link VisitorAuthRequest} message to RabbitMQ.
     * The request is converted to JSON before being sent to the exchange.
     *
     * @param visitorAuthRequest the visitor authorization request to be sent.
     */
    public void sendMessage(VisitorAuthRequest visitorAuthRequest) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(visitorAuthRequest);
            template.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, jsonMessage); // Update
                                                                                                            // the usage
        } catch (JsonProcessingException e) {
            return;
        }
    }
}
