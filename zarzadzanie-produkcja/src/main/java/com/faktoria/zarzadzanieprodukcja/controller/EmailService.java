package com.faktoria.zarzadzanieprodukcja.controller;

import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendPasswordChangeEmail(String userEmail) {
        // Skonfiguruj właściwości SMTP jak pokazano w poprzednim przykładzie
        // Twórz i wysyłaj wiadomość
        sendEmail(userEmail, "Zmiana hasła", "Twoje hasło zostało zmienione.");
    }

}
