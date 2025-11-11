//package dev.kk.mail;
//
//import dev.kk.mail.handle.ContentRecord;
//import dev.kk.mail.handle.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class LoaderRun implements CommandLineRunner {
//
//    @Autowired
//    EmailService emailService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        ContentRecord record = new ContentRecord("Hoàng", "Kapi", "https://travandon.com/dia-chi-buu-dien-tai-thanh-xuan-ha-noi.html");
//        emailService.sendEmail(record, "example@gmail.com");
//    }
//}
