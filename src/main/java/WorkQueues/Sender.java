package WorkQueues;

import com.rabbitmq.client.Channel;
import util.ConnectionManagement;

import java.util.concurrent.TimeoutException;

/**
 * Created by satheesh on 22/3/17.
 */
public class Sender {
    private static String QUEUE_NAME = "RMQ";

    private static String getMessage(String[] strings){
        if (strings.length < 1)
            return "Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

    public static void main(String[] argv)
            throws java.io.IOException, TimeoutException {
        Channel channel = ConnectionManagement.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String[] inputWords = new String[]{"Hello...", "This", "is", "RabbitMQ"};
        String message = getMessage(inputWords);
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        ConnectionManagement.close();
    }
}
