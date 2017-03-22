package WorkQueues;

import com.rabbitmq.client.*;
import util.ConnectionManagement;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by satheesh on 22/3/17.
 */
public class Receiver {
    private static String QUEUE_NAME = "RMQ";


    public static void main(String[] argv) throws java.io.IOException,
            java.lang.InterruptedException, TimeoutException {
        Channel channel = ConnectionManagement.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                }
            }
        };
        boolean autoAck = true; // acknowledgment is covered below
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);

    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }
}
