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
import javax.validation.ValidationException;

import org.Kader.config.MailConfig;
import org.Kader.entities.EmailMessage;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
	
   @Autowired
   private MailConfig mailConfig;
	
	@RequestMapping(value="/send",method = RequestMethod.POST)
	public String sendEail(@RequestBody EmailMessage emailMessage,BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()) {
			throw new ValidationException("EmailMessage is not valid");
		}
		sendMail(emailMessage);
		return "Email send successufly ";
		
	}
	
	private void sendMail(EmailMessage emailMessage) throws Exception {
		
		/*Properties props =new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");*/
		
		JavaMailSenderImpl javaMail=new JavaMailSenderImpl();
		javaMail.setHost(mailConfig.getHost());
		javaMail.setPort(mailConfig.getPort());
		javaMail.setUsername(mailConfig.getUsername());
		javaMail.setPassword(mailConfig.getPassword());
		
		
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		mailMessage.setFrom(emailMessage.getFrom());
		mailMessage.setTo(emailMessage.getTo());
		mailMessage.setSubject(emailMessage.getSubject());
		mailMessage.setText(emailMessage.getBody());
		
		javaMail.send(mailMessage);
		
		/*Session session=Session.getInstance(props, new javax.mail.Authenticator() {
			
			   protected PasswordAuthentication getPassowordAuthentication() {
				   return new PasswordAuthentication(username,password);
			   }   
		});
		*/
		
		
		/*Message msg =new MimeMessage(session);
		msg.setFrom(new InternetAddress(username, false));
		
		msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailMessage.getTo_Address()));
		msg.setSubject(emailMessage.getSubject());
		msg.setContent(emailMessage.getBody(),"text/html");
		msg.setSentDate(new Date());
		
		MimeBodyPart mimeBodyPart=new MimeBodyPart();
		mimeBodyPart.setContent(emailMessage.getBody(), "text/html");
		
		
		
		/*Multipart multipart=new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);
        
		MimeBodyPart attachPart=new MimeBodyPart();
		
		attachPart.attachFile("C:\\Users\\abdel\\OneDrive\\Images\\Captures d’écran\\pagehome.PNG");
		multipart.addBodyPart(attachPart);
		msg.setContent(multipart);*/
		//send the mail
		//Transport.send(msg);
		
		
	}

}
