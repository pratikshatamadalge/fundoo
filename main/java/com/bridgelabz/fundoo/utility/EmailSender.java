package com.bridgelabz.fundoo.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.bridgelabz.fundoo.model.MailData;

public class EmailSender {

	@Autowired
	JavaMailSender javaMailSender;

	public void emailSender(MailData mailData) {
		System.out.println("hie");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("pratikshatamadalge@gmail.com");
		message.setTo(mailData.getEmailId());
		message.setSubject("varify user");
		message.setText("Varification token: " + mailData.getToken());
		javaMailSender.send(message);
	}
}
