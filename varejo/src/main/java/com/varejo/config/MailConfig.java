//package com.varejo.config;
//
//	
//	import org.springframework.context.annotation.Bean;
//	import org.springframework.context.annotation.Configuration;
//	import org.springframework.mail.javamail.JavaMailSender;
//	import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//	import java.util.Properties;
//
//	@Configuration
//	public class MailConfig {
//
//		@Bean
//		public JavaMailSender javaMailSender() {
//		    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		    mailSender.setHost("smtp.gmail.com");
//		    mailSender.setPort(587);
//		    mailSender.setUsername(System.getenv("MAIL_USERNAME")); // usar variável de ambiente
//		    mailSender.setPassword(System.getenv("MAIL_PASSWORD")); // usar variável de ambiente
//
//		    Properties props = mailSender.getJavaMailProperties();
//		    props.put("mail.transport.protocol", "smtp");
//		    props.put("mail.smtp.auth", "true");
//		    props.put("mail.starttls.enable", "true");
//		    props.put("mail.debug", "true");
//
//		    return mailSender;
//		}
//	}
//
//
