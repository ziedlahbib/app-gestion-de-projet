package com.example.appgestiondeprojet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jakarta.mail.SendFailedException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;

@Service("emailService")
@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("spring.mail.username")
	private String from; 
	
    public void sendEmail(final String to, final String subject, final String body) throws SendFailedException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

	public void sendEmailWithAttachment(final String to, final String subject, final String message, final File attachment) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText("<html><body>" + message + "</html></body>", true);
			FileSystemResource file = new FileSystemResource(attachment);
			helper.addAttachment(attachment.getName(), file);
			javaMailSender.send(mimeMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	@Async
	public void sendEmail(SimpleMailMessage email) {
		javaMailSender.send(email);
	}

}
