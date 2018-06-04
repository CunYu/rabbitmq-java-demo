import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DefaultExchangeConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(Constant.QUEUE_ONE, true, false, false, null);
        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {

                String message = new String(body, "UTF-8");
                System.out.println(message + " " +envelope.getDeliveryTag());
            }
        };
        // 设置Consumer最多同时处理的数量
        channel.basicQos(1);
        // 自动返回acknowledgment
        channel.basicConsume(Constant.QUEUE_ONE, true, consumer);
    }
}