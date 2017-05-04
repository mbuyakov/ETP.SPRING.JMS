package ru.mos.etp.listener;

import java.nio.charset.StandardCharsets;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("Process " + message.getClass().getName() + " " + message.getJMSMessageID() + " "
					+ message.getJMSTimestamp());

			String body = message instanceof TextMessage ? ((TextMessage) message).getText() : null;
			if (message instanceof BytesMessage) {
				int length = (int) ((BytesMessage) message).getBodyLength();
				byte[] bytes = new byte[length];
				((BytesMessage) message).readBytes(bytes);
				body = new String(bytes, StandardCharsets.UTF_8);
			}
			
			System.out.println("Message body: " + body);

		} catch (Exception e) {
			System.out.println("Can't process message: " + e);
			throw new IllegalArgumentException("Can't process message: " + e, e);
		}
	}

}
