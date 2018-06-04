import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class HeadersExchangeProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> headers = new HashMap<>();
        headers.put("color", "red");
        headers.put("size", "little");
        Builder builder = new Builder();
        builder.headers(headers);
        builder.deliveryMode(2);

        channel.exchangeDeclare(Constant.EXCHANGE_FOUR, Constant.ExchangeType.HEADERS.getValue());
        // headers类型交换路由会将信息分发给所有与Exchange绑定的，消息头匹配的队列。
        channel.basicPublish(Constant.EXCHANGE_FOUR, "", builder.build(), Constant.MESSAGE.getBytes());

        channel.close();
        connection.close();
    }
}