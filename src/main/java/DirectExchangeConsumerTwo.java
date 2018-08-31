import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectExchangeConsumerTwo {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(Constant.QUEUE_FIVE, true, false, false, null);
        channel.exchangeDeclare(Constant.EXCHANGE_TWO, Constant.ExchangeType.DIRECT.getValue());
        channel.queueBind(Constant.QUEUE_FIVE, Constant.EXCHANGE_TWO, "two");

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
        channel.basicConsume(Constant.QUEUE_FIVE, false, consumer);
    }
}