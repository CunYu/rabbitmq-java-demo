import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicExchangeProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constant.EXCHANGE_THREE, Constant.ExchangeType.TOPIC.getValue());
        // routingKey为one.two topic类型交换路由会将信息分发给与其绑定的,routingKey模糊匹配one.two的队列
        channel.basicPublish(Constant.EXCHANGE_THREE, "one.two.three", null, Constant.MESSAGE.getBytes());

        channel.close();
        connection.close();
    }
}