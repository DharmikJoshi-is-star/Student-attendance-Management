package com.studentattendancesystem.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService{

	@Autowired
	private MailSender mailSender;
	
	
	public void sendMail(String receiver, String subject, String body) throws MailException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(receiver);
		message.setText(body);
		message.setSubject(subject);
		mailSender.send(message);
	}

	
}
