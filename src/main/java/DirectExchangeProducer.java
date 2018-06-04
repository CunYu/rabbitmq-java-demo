import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectExchangeProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constant.EXCHANGE_TWO, Constant.ExchangeType.DIRECT.getValue());
        // routingKey为one direct类型交换路由会将信息分发给与其绑定的,routingKey为one的队列
        channel.basicPublish(Constant.EXCHANGE_TWO, "one", null, Constant.MESSAGE.getBytes());

        channel.close();
        connection.close();
    }
}