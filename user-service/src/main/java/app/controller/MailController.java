package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

@Controller
public class MailController {
    private JavaMailSender emailSender;
    @Autowired
    public MailController(JavaMailSender emailSender){ this.emailSender = emailSender;}
    public void sendSimpleMessage(String to, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("larionovags1999@gmail.com");
        message.setTo(to);
        message.setSubject("Weather");
        message.setText(msg);
        emailSender.send(message);
    }
}
