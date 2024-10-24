package com.varejo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;
	
	/**
     * Método para enviar um e-mail simples com os parâmetros fornecidos.
     *
     * @param para O endereço de e-mail do destinatário.
     * @param assunto O assunto do e-mail.
     * @param texto O corpo do e-mail.
     */
	public void enviarEmail(String para, String assunto, String texto) {
		SimpleMailMessage mensagem	= new SimpleMailMessage();
		mensagem.setTo(para);
		mensagem.setSubject(assunto);
		mensagem.setText(texto);
		emailSender.send(mensagem);
	}
}
