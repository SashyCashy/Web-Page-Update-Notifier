package com.sdsu.assignment4;

import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class Mail implements Observer {
	private boolean mailFlag = false;@Override
	public void update(Observable subjectChange, Object notifyMessage) {
		final String username = "sashpre001@gmail.com";
		final String password = "*********";
		mailFlag = true;
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", "true");
		mailProperties.put("mail.smtp.starttls.enable", "true");
		mailProperties.put("mail.smtp.host", "smtp.gmail.com");
		mailProperties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(mailProperties,
		new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sashpre001@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse("sashpre001@gmail.com"));
			message.setSubject("Updating URL " + notifyMessage.toString());
			message.setText("The webpage " + notifyMessage.toString() + " has been updated");
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean getMailFlag() {
		return mailFlag;
	}
}