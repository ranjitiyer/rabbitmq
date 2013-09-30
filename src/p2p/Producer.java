package p2p;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {
		/* Connection */
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		
		/* Establish a channel */
		Channel channel = connection.createChannel();

		/* Declare the Queue */
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		/* Send a message */
		String message = "Hello World!";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		
		/* Close and terminate */
		channel.close();
	    connection.close();
	}
}
