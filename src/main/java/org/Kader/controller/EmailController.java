package org.Kader.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.Kader.entities.EmailMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class EmailController {
	
	@Value("${gmail.username}")
	private String username;
	@Value("${gmail.password}")
	private String password; 
	
	@RequestMapping(value="/sendMail",method = RequestMethod.POST)
	public String sendEail(@RequestBody EmailMessage emailMessage) throws Exception {
		sendMail(emailMessage);
		return "Email send successufly ";
		
	}
	
	private void sendMail(EmailMessage emailMessage) throws Exception {
		
		Properties props =new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttis.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session=Session.getInstance(props, new javax.mail.Authenticator() {
			
			   protected PasswordAuthentication getPassowordAuthentication() {
				   return new PasswordAuthentication(username,password);
			   }   
		});
		
		
		
		Message msg =new MimeMessage(session);
		msg.setFrom(new InternetAddress(username, false));
		
		msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailMessage.getTo_Address()));
		msg.setSubject(emailMessage.getSubject());
		msg.setContent(emailMessage.getBody(),"text/html");
		msg.setSentDate(new Date());
		
		MimeBodyPart mimeBodyPart=new MimeBodyPart();
		mimeBodyPart.setContent(emailMessage.getBody(), "text/html");
		
		Multipart multipart=new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);
        
		MimeBodyPart attachPart=new MimeBodyPart();
		
		attachPart.attachFile("C:\\Users\\abdel\\OneDrive\\Images\\Captures d’écran\\pagehome.PNG");
		multipart.addBodyPart(attachPart);
		msg.setContent(multipart);
		//send the mail
		Transport.send(msg);
		
		
	}

}
