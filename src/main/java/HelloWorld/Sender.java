package HelloWorld;

import com.rabbitmq.client.Channel;
import util.ConnectionManagement;

import java.util.concurrent.TimeoutException;

/**
 * Created by satheesh on 22/3/17.
 */
public class Sender {
    private static String QUEUE_NAME = "RMQ";
    public static void main(String[] argv)
            throws java.io.IOException, TimeoutException {
        Channel channel = ConnectionManagement.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        ConnectionManagement.close();
    }
}
