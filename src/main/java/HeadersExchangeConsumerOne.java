import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class HeadersExchangeConsumerOne {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> headers = new HashMap<>();
        headers.put(Constant.X_MATCH, "any");
        headers.put("color", "red");
        headers.put("size", "big");

        channel.queueDeclare(Constant.QUEUE_EIGHT, true, false, false, null);
        channel.exchangeDeclare(Constant.EXCHANGE_FOUR, Constant.ExchangeType.HEADERS.getValue());
        channel.queueBind(Constant.QUEUE_EIGHT, Constant.EXCHANGE_FOUR, "", headers);

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {

                String message = new String(body, "UTF-8");
                System.out.println(message + " " +envelope.getDeliveryTag());
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicQos(1);
        channel.basicConsume(Constant.QUEUE_EIGHT, false, consumer);
    }
}