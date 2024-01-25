package backend;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Mail {

	// Learnt how to send an email from here
	// https://mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
	// Learnt how to set up a gmail SMTP server from here
	// https://www.youtube.com/watch?v=yuOK6D7deTo
	public static void sendEmailTo(String receiver, String Tyre, int tyresLeft) {

		// Username is the gmail that you are sending the email from
		String username = "pisittangwongsiri@gmail.com";

		// Password is the app password that is generated on the google account
		String password = "rhtuevcktuudulbl";

		// SMTP config and port settings
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); // TLS

		// Connecting to server and creating a session
		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@gmail.com"));
			
			//This is the line where we determine who receives the email (using receiver parameter for now)
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
			
			//Setting subject of the email
			message.setSubject(Tyre + " is running out of stock!");
			
			//Writing the message in the email (TIP: \n is starting a new line)
			message.setText("Dear whom it may concern," + "\n\n" + Tyre + " is running out of stock. There are only " + tyresLeft + " tyres left." + "\n\n Sincerely," + "\n Stock Management Team");

			Transport.send(message);

			//The system saying that the email is sent
			System.out.println("Email sent successfully");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
