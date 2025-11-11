package dev.kk.mail.handle;

import dev.kk.mail.utils.ConvertObject;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

    final JavaMailSenderImpl sender;
    final TemplateEngine templateEngine;

    EmailServiceImpl(JavaMailSenderImpl sender, TemplateEngine templateEngine) {
        this.sender = sender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendEmail(ContentRecord content, String to) throws MessagingException, IllegalAccessException, IOException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Context context = new Context();
        context.setVariables(ConvertObject.ObjToMap(content));
        var htmlContent = templateEngine.process("mail", context);
        helper.setText(htmlContent, true);
        helper.setSubject("Đăng ký công ty");
        helper.setFrom(new InternetAddress("company@gmail.com", "KK-Team"));
        helper.setTo(InternetAddress.parse(to));
        var file = Objects.requireNonNull(EmailServiceImpl.class.getClassLoader()
                .getResource("images/dejavu.pdf")).getFile();
        FileSystemResource res = new FileSystemResource(file);
        helper.addAttachment("dejavu.pdf", res);
        sender.send(message);
    }

    @Override
    public void fallback(ContentRecord content, String to, CallNotPermittedException e) {
        System.out.println("fallback CallNotPermittedException");
    }

    @Override
    public void fallback(ContentRecord content, String to, NumberFormatException e) {
        System.out.println("fallback NumberFormatException");
    }

    @Override
    public void fallback(ContentRecord content, String to, Exception e) {
        System.out.println("fallback Exception");
    }
}
