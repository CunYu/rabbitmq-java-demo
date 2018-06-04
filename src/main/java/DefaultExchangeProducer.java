import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DefaultExchangeProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        // 声明队列 同一队列名配置需一致
        // durable:是否持久化
        // autoDelete为true 消费者断开时自动删除 (无消费者时不删除)
        channel.queueDeclare(Constant.QUEUE_ONE, true, false, false, null);
        // 使用默认交换路由发送消息 routingKey为队列名称
        // MessageProperties.PERSISTENT_TEXT_PLAIN 设置消息为持久化 也可以为null
        // BasicProperties对象中deliveryMode为1表示不持久化，为2表示持久化
        channel.basicPublish("", Constant.QUEUE_ONE, MessageProperties.PERSISTENT_TEXT_PLAIN, Constant.MESSAGE.getBytes());

        channel.close();
        connection.close();
    }
}