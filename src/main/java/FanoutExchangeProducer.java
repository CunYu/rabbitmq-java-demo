import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutExchangeProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        // 声明交换路由
        channel.exchangeDeclare(Constant.EXCHANGE_ONE, Constant.ExchangeType.FANOUT.getValue());
        // routingKey为"" fanout类型交换路由会将信息分发给每一个绑定的队列
        channel.basicPublish(Constant.EXCHANGE_ONE, "", null, Constant.MESSAGE.getBytes());

        channel.close();
        connection.close();
    }
}