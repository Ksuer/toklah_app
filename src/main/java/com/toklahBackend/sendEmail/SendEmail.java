package com.toklahBackend.sendEmail;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {
	
	@Value("${mail.host}")
	private String host;
	@Value("${mail.username}")
	private String username;
	@Value("${mail.password}")
	private String password;
	@Value("${spring.mail.properties.mail.smtp.port}")
	private String port;
	
@Autowired
private JavaMailSender javaMailSender;



@Bean
public JavaMailSender getJavaMailSender() {
	
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(this.host);
    if (port == null){
    	mailSender.setPort(25);
    }else {
    	mailSender.setPort(Integer.parseInt(port));
    }
     
    mailSender.setUsername(this.username);
    mailSender.setPassword(this.password);
     
    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");
     
    return mailSender;
}


public void sendMail(String to,String body, String title) {
    System.out.println("Sending email...");

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setFrom("Toklahapp@gmail.com");
    message.setSubject("Forgot your password? ");
    message.setText(body);
    javaMailSender.send(message);

    System.out.println("Email Sent!");	
    }


}
