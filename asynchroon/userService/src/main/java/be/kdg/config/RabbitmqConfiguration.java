package be.kdg.config;

import com.google.common.collect.Lists;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by nadya on 3/04/2017.
 */
@Configuration
public class RabbitmqConfiguration
{



   @Bean
    public RabbitTemplate rabbitTemplate( ConnectionFactory connectionFactory)
    {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }



    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(ParametersBinding.EXCHANGE_NAME);
    }
    @Bean
    public TopicExchange appExchange2() {
        return new TopicExchange(ParametersBinding.EXCHANGE_NAME2);
    }
    @Bean
    public Queue appQueueName() {
        return new Queue(ParametersBinding.QUEUE_NAME);
    }

    @Bean
    public Queue appQueueName2() {
        return new Queue(ParametersBinding.QUEUE_NAME2);
    }

    @Bean
    public Binding declareBindingGeneric() {
        return BindingBuilder.bind(appQueueName()).to(appExchange()).with(ParametersBinding.ROUTING_KEY);
    }

    @Bean
    public Binding declareBindingSpecific() {
        return BindingBuilder.bind(appQueueName2()).to(appExchange2()).with(ParametersBinding.ROUTING_KEY2);
    }




    @Bean
    public MessageConverter jsonMessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

}
