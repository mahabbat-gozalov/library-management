package com.mg_devjoint.library_management.service.impl;

import com.mg_devjoint.library_management.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public boolean sendTemporaryPasswordEmail(String email, String temporaryPassword) {
        try {

            String htmlBody = buildTemporaryPasswordEmailBody(temporaryPassword);

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Your Library Management account is ready");
            helper.setText(htmlBody, true);

            mailSender.send(message);

            return true;

        } catch (MessagingException | MailException e) {
            log.error("Error sending temporary password email", e);
            return false;
        }
    }

    private String buildTemporaryPasswordEmailBody(String temporaryPassword) {
        return """
                <html>
                  <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 24px;">
                    <div style="max-width: 480px; margin: 0 auto; background: #ffffff; border-radius: 8px; padding: 32px;">
                      <h2 style="color: #2c3e50;">Welcome to Library Management</h2>
                      <p>Your account has been created. Use the temporary password below to log in:</p>
                      <p style="font-size: 20px; font-weight: bold; letter-spacing: 1px; background: #f0f0f0; padding: 12px 16px; border-radius: 6px; text-align: center;">
                        %s
                      </p>
                      <p style="color: #666666; font-size: 13px;">
                        For security reasons, please log in and change this password as soon as possible.
                      </p>
                    </div>
                  </body>
                </html>
                """.formatted(temporaryPassword);
    }
}
