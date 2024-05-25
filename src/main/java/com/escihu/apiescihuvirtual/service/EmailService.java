package com.escihu.apiescihuvirtual.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final Environment env;

    public EmailService(JavaMailSender emailSender, Environment env) {
        this.emailSender = emailSender;
        this.env = env;
    }
    //TODO: documentar
    public void sendUserCredencials(String to, String username, String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Tus credenciales de acceso a EsciHu Virtual");
        helper.setText("Dear user,\n\nYour account has been created.\n\nUsername: " + username + "\nPassword: " + password + "\n\nBest regards,\nYour Team");

        emailSender.send(message);

    }
    //TODO: documentar
    public void sendForgotPasswordEmail(String to) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String port = env.getProperty("server.port");
        helper.setTo(to);
        helper.setSubject("Recuperación de contraseña");
        helper.setText("""
                <diu>
                    <a href="http://localhost:%s/set-password?email=%s">Click here to reset your password</a>
                </div>
                """.formatted(port,to), true);

        emailSender.send(message);
    }
}
