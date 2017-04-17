package be.kdg.config;

/**
 * Created by nadya on 3/04/2017.
 */
public class ParametersBinding
{
    public final static String QUEUE_NAME = "avatar";
    public final static String EXCHANGE_NAME = "avatarExchange";
    public final static String ROUTING_KEY = QUEUE_NAME;

    public final static String QUEUE_NAME2 = "mail";
    public final static String EXCHANGE_NAME2 = "mailExchange";
    public final static String ROUTING_KEY2 = QUEUE_NAME2;

    public final static String QUEUE_NAME3 = "user";

    /*
        1. Messages will be published to an exchange.
        2. Queues will be bound to exchange based on routingKeys.
        3. RabbitMQ will forward messages with matching routing keys to the corresponding queues.
     */

}
