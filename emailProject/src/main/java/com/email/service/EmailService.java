package com.email.service;

import java.io.File;
import java.util.Properties;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.email.controller.EmailController;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

@Service
public class EmailService {
	private org.slf4j.Logger logger = LoggerFactory.getLogger(EmailService.class);
	
		public boolean sendEmailWithFile(String to , String subject , String text , File file) {
			boolean flag = false;
			
			Properties properties = new Properties();
			properties.put("mail.smtp.auth",true);
			properties.put("mail.smtp.starttls.enable", true);
			properties.put("mail.smtp.port","587");
			properties.put("mail.smtp.host" , "smtp.gmail.com");
			
			String username = "aarambhmourya";
			String password = "lljtboueadmvntui";
			String from 	= "aarambhmourya@gmail.com";
			
			Session session = Session.getInstance(properties , new Authenticator(){
				
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username , password);
				}
			});
			 
			try {
				Message message = new MimeMessage(session);
				
				message.setRecipient(Message.RecipientType.TO , new InternetAddress(to));
				message.setFrom(new InternetAddress(from));
				message.setSubject(subject);
				
				MimeBodyPart part1 = new MimeBodyPart();
				part1.setText(text);
				
				MimeBodyPart part2 = new MimeBodyPart();
				part2.attachFile(file);
				
				MimeMultipart mimeMultipart = new MimeMultipart();
				mimeMultipart.addBodyPart(part1);
				mimeMultipart.addBodyPart(part2);
				
				message.setContent(mimeMultipart);
				
				Transport.send(message);
				
				flag = true;
			} catch (Exception e) {
				logger.error("sendEmailWithFile method exception :::" +e);
			}
			return flag;
		}
		
		
		public boolean sendEmail(String to , String subject , String text) {
			boolean flag = false;
			
			Properties properties = new Properties();
			properties.put("mail.smtp.auth",true);
			properties.put("mail.smtp.starttls.enable", true);
			properties.put("mail.smtp.port","587");
			properties.put("mail.smtp.host" , "smtp.gmail.com");
			
			String username = "aarambhmourya";
			String password = "lljtboueadmvntui";
			String from 	= "aarambhmourya@gmail.com";
			
			Session session = Session.getInstance(properties , new Authenticator(){
				
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username , password);
				}
			});
			 
			try {
				Message message = new MimeMessage(session);
				
				message.setRecipient(Message.RecipientType.TO , new InternetAddress(to));
				message.setFrom(new InternetAddress(from));
				message.setSubject(subject);
				message.setText(text);
				
				Transport.send(message);
				
				flag = true;
			} catch (Exception e) {
				logger.error("sendEmail method exception :::" +e);
			}
			return flag;
		}
}
