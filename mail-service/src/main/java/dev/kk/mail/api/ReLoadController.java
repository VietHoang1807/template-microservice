package dev.kk.mail.api;

import dev.kk.mail.handle.ContentRecord;
import dev.kk.mail.handle.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reload")
public class ReLoadController {

    @Autowired
    EmailService emailService;

    @GetMapping
    public String reloadSendMail() {
        try {
            var record= new ContentRecord("Hoàng", "Kapi", "https://travandon.com/dia-chi-buu-dien-tai-thanh-xuan-ha-noi.html");
            emailService.sendEmail(record, "chauviethoang1807@gmail.com");
        } catch (Exception e) {
            return "reload failure";
        }
        return "reload success";
    }
}
