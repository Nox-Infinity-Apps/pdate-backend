package com.noxinfinity.pdating.Domains.MailManagement;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    // Hàm gửi email
    public void sendEmail(String to, String subject, String text) throws MailException, MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // Cấu hình email
        helper.setFrom("pdatead@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true); // true để gửi email dạng HTML

        // Gửi email
        javaMailSender.send(mimeMessage);
    }

    public static String readHtmlFile(String filePath) {
        Path path = Paths.get(filePath);

        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
