package ar.edu.utn.frc.tup.lc.iv.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ settings, including queue, exchange, and
 * routing key definitions.
 * It also configures message conversion to JSON using Jackson.
 */
@Configuration
public class RabbitMQConfig {

    /**
     * Name of the RabbitMQ queue.
     */
    public static final String QUEUE_NAME = "my_queue";

    /**
     * Name of the RabbitMQ exchange.
     */
    public static final String EXCHANGE_NAME = "my_exchange";

    /**
     * Routing key used to bind the queue to the exchange.
     */
    public static final String ROUTING_KEY = "my_routing_key";

    /**
     * Defines the RabbitMQ queue.
     *
     * @return a new {@link Queue} with the name {@value #QUEUE_NAME}.
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    /**
     * Defines the RabbitMQ exchange of type {@link TopicExchange}.
     *
     * @return a new {@link TopicExchange} with the name {@value #EXCHANGE_NAME}.
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    /**
     * Binds the defined queue to the exchange using a routing key.
     *
     * @param queue    the RabbitMQ queue.
     * @param exchange the RabbitMQ exchange.
     * @return a {@link Binding} between the queue and the exchange with the
     *         specified routing key.
     */
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    /**
     * Configures the {@link RabbitTemplate} for sending messages.
     *
     * @param connectionFactory the RabbitMQ {@link ConnectionFactory}.
     * @return a configured {@link RabbitTemplate} with a JSON message converter.
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * Configures the message converter to use Jackson for JSON serialization.
     *
     * @return a {@link Jackson2JsonMessageConverter} for JSON message conversion.
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
