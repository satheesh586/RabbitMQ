package HelloWorld;

import com.rabbitmq.client.*;
import util.ConnectionManagement;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by satheesh on 22/3/17.
 */
public class Receiver {
    private static String QUEUE_NAME = "RMQ";

    public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException, TimeoutException {
        Channel channel = ConnectionManagement.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
