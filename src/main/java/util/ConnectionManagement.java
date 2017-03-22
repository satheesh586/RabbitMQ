package util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by satheesh on 22/3/17.
 */
public class ConnectionManagement {
    private static Connection connection;
    private static String host = "localhost";

    static {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static Channel getChannel() throws IOException, TimeoutException {
        Channel channel = connection.createChannel();
        return channel;
    }

    public static void close() throws IOException {
        connection.close();
    }
}
