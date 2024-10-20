//package com.varejo.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//	@Autowired
//	private JavaMailSender emailSender;
//	
//	public void enviarEmail(String para, String assunto, String texto) {
//		SimpleMailMessage mensagem	= new SimpleMailMessage();
//		mensagem.setTo(para);
//		mensagem.setSubject(assunto);
//		mensagem.setText(texto);
//		emailSender.send(mensagem);
//	}
//}
