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
    private final Environment environment;

    public EmailService(JavaMailSender emailSender, Environment environment) {
        this.emailSender = emailSender;
        this.environment = environment;
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
  
    //TODO: Documentacion
    public void sendForgotPasswordEmail(String to) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String port = environment.getProperty("server.port");
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
