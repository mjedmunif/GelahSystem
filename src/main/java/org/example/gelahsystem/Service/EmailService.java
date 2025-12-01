package org.example.gelahsystem.Service;
import jakarta.mail.MessagingException;
import org.springframework.core.io.ByteArrayResource;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {


    private final JavaMailSender mailSender;

    public void sendEmailWithPdf(String to , String subject, String body, byte[] pdfBytes, String fileName){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            helper.addAttachment(fileName, new ByteArrayResource(pdfBytes));

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email with PDF", e);
        }
    }
    }

