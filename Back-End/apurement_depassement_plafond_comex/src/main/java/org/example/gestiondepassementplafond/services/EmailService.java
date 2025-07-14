package org.example.gestiondepassementplafond.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text, String... copyTo) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("afriland@afrilandfirstbank.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        if (copyTo != null && copyTo.length > 0) {
            message.setCc(copyTo);
        }

        mailSender.send(message);
    }

    public boolean sendEmailWithAttachments(String to, String subject, String text, List<File> attachments, String... copyTo) throws MessagingException {

        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("afriland@afrilandfirstbank.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            if (copyTo != null && copyTo.length > 0) {
                helper.setCc(copyTo); // ✅ Ici ça fonctionne
            }

            // Ajouter les pièces jointes
            if (attachments != null) {
                for (File file : attachments) {
                    FileSystemResource resource = new FileSystemResource(file);
                    helper.addAttachment(file.getName(), resource);
                }
            }

            mailSender.send(message);

            return true;
        } catch (Exception e) {
            log.info("Exception --------");
            log.error(e.getMessage());
            return false;
        }
    }

}
