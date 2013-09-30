package workq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Worker {

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws Exception {
		/* Create connection */
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		/* Declare a durable queue */
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

		/* Configure quality of service options */
		channel.basicQos(1);

		/* Create consumer */
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(TASK_QUEUE_NAME, false, consumer);

		while (true) {
			/* Get a job */
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());

			/* Do the work */
			System.out.println(" [x] Received '" + message + "'");
			doWork(message);
			System.out.println(" [x] Done");

			/* Send Ack. We have processed the job and it can be removed from the Queue */
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}         
	}

	private static void doWork(String task) throws InterruptedException {
		for (char ch: task.toCharArray()) {
			if (ch == '.') Thread.sleep(1000);
		}
	}
}
