package freela.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.*;

public class DoMail {
	public static String SMTP_HOST_NAME = "localhost"; // or
														// simply
														// "localhost"
	public static String SMTP_AUTH_USER = "ismet@freela.jvmhost.net";
	public static String SMTP_AUTH_PWD = "ZMA8~~3vu~iu";
	public static String emailMsgTxt = "Body";
	public static String emailSubjectTxt = "Subject";
	public static String emailFromAddress = "ismet@freela.jvmhost.net";

	// Add List of Email address to who email needs to be sent to
	public static String[] emailList = { "ismettung@gmail.com" };

	public static void main(String args[]) throws Exception {
		DoMail.postMail(emailList, emailSubjectTxt, emailMsgTxt,
				emailFromAddress);
		System.out.println("Sucessfully Sent mail to All Users");
	}

	public static void postMail(String recipients[], String subject, String message,
			String from) throws MessagingException,
			AuthenticationFailedException {

		boolean debug = false;
		from = emailFromAddress;
		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");
		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);

		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/html; charset=utf-8");
		Transport.send(msg);
	}

	public static class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}
}