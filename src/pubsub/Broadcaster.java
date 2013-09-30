package pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Broadcaster {
	private static final String EXCHANGE_NAME = "healthcheck";
	
	private static final String HEART_BEAT = "server-is-alive";

	public static void main(String[] argv) throws Exception {
		/* Connection */
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		/* Declare a Exchange. A exchange is a broker that supports various routing rules
		 * Fanout means broadcast it to all clients. Each client has its own queue and the message
		 * is put on each queue. */		
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

		/* Broadcase message */
		channel.basicPublish(EXCHANGE_NAME, "", null, HEART_BEAT.getBytes());

		/* Close */
		channel.close();
		connection.close();
	}
}
