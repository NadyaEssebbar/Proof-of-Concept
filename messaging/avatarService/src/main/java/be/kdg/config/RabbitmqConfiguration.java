package be.kdg.config;

import be.kdg.domain.User;
import be.kdg.services.Receiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by nadya on 1/04/2017.
 */
@Configuration
public class RabbitmqConfiguration
{
    @Bean
    public RabbitTemplate rabbitTemplate( ConnectionFactory connectionFactory)
    {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setQueue(ParametersBinding.QUEUE_NAME2);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(ParametersBinding.EXCHANGE_NAME2);
    }


    @Bean
    public Queue appQueueName2() {
        return new Queue(ParametersBinding.QUEUE_NAME2);
    }

    @Bean
    public Binding declareBindingGeneric() {
        return BindingBuilder.bind(appQueueName2()).to(appExchange()).with(ParametersBinding.ROUTING_KEY2);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(ParametersBinding.QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        container.setMessageConverter(jsonMessageConverter());
        return container;
    }


    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    public MessageConverter jsonMessageConverter()
    {
        return new Jackson2JsonMessageConverter();

    }
}
