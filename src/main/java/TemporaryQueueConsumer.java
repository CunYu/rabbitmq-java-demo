import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TemporaryQueueConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        // 临时队列，一旦消费者断开，队列就自动删除了，非持久的，唯一的。
        String queueName = channel.queueDeclare().getQueue();
        channel.exchangeDeclare(Constant.EXCHAGE_FIVE, Constant.ExchangeType.FANOUT.getValue());
        channel.queueBind(queueName, Constant.EXCHAGE_FIVE, "");
        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {

                String message = new String(body, "UTF-8");
                System.out.println(message + " " + envelope.getDeliveryTag());
            }
        };
        channel.basicQos(1);
        channel.basicConsume(queueName, true, consumer);
    }
}