import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutExchangeConsumerOne {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(Constant.QUEUE_TWO, true, false, false, null);
        // 绑定
        channel.queueBind(Constant.QUEUE_TWO, Constant.EXCHANGE_ONE, "");

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {

                String message = new String(body, "UTF-8");
                System.out.println(message + " " +envelope.getDeliveryTag());
                // 手动返回acknowledgment
                // envelope.getDeliveryTag() 编号 通道中的消息编号都是从1开始，相当于给每个通道中的信息编号
                // false 表示返回当前acknowledgment
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicQos(1);
        // 关闭自动返回acknowledgment
        channel.basicConsume(Constant.QUEUE_TWO, false, consumer);
    }
}