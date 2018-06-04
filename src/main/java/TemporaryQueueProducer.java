import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TemporaryQueueProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constant.EXCHAGE_FIVE, Constant.ExchangeType.FANOUT.getValue());
        channel.basicPublish(Constant.EXCHAGE_FIVE, "", MessageProperties.PERSISTENT_TEXT_PLAIN, Constant.MESSAGE.getBytes());

        channel.close();
        connection.close();
    }
}