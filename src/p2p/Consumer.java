package p2p;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer {
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		/* Connection */
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		
		/* Establish a channel */
		Channel channel = connection.createChannel();

		/* Declare the Queue */
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		/* Bind the Consumer to the Queue */
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);

		/* Consume messages */
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
		}

	}	

}
