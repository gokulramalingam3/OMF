package com.omf.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.omf.service.EmailService;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

	@Override
    public void sendSimpleMessage(
      String to, String subject, String text) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true); 
	    helper.setFrom("noreply@omf.com");
	    helper.setTo(to); 
	    helper.setSubject(subject); 
	    message.setContent(text, "text/html");
        emailSender.send (message);
    }
}