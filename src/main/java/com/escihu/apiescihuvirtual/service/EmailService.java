package com.escihu.apiescihuvirtual.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Servicio para enviar correos electrónicos
 *
 */
@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender emailSender;
    private final Environment env;

    public EmailService(JavaMailSender emailSender, Environment env) {
        this.emailSender = emailSender;
        this.env = env;
    }

    // TODO: Manejar excepciones en caso de se configutr stmp para gmail
    /**
     * Envia un correo con las credenciales de acceso al usuario
     * @param to - Correo del destinatario
     * @param username - Nombre de usuario
     * @param password - Contraseña del usuario
     */
    public void sendUserCredencials(String to, String username, String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Tus credenciales de acceso a EsciHu Virtual");
        helper.setText("Dear user,\n\nYour account has been created.\n\nUsername: " + username + "\nPassword: " + password + "\n\nBest regards,\nYour Team");

        try {
            emailSender.send(message);

        } catch (MailException e) {
            LOGGER.error("Error sending email to {}", to, e);
            //TODO: Implementar un mensaje de error en caso de que no se pueda enviar el correo

        }

    }

    /**
     * Envia un correo con un enlace para restablecer la contraseña
     * @param to - Correo del destinatario
     */
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
                """.formatted(port, to), true);

        try {
            emailSender.send(message);
        } catch (MailException e) {
            LOGGER.error("Error sending email to {}", to, e);
            //TODO: Implementar un mensaje de error en caso de que no se pueda enviar el correo
        }
    }
}
