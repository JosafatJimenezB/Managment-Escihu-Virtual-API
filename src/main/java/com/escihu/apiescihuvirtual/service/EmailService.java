package com.escihu.apiescihuvirtual.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendUserCredencials(String to, String username, String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        message.setFrom("escihuvirtual@escihupuebla.edu.mx");
        helper.setTo(to);
        helper.setSubject("Tus credenciales de acceso a EsciHu Virtual");
        helper.setText("Dear user,\n\nYour account has been created.\n\nUsername: " + username + "\nPassword: " + password + "\n\nBest regards,\nYour Team");

        emailSender.send(message);

    }
}
