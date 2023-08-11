package com.example.gmailPractice;

import com.google.api.services.gmail.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class SendMailController {
    @Autowired
    SendMailService sendMailService;

    @GetMapping("/draft")
    public Object delLabel() throws GeneralSecurityException, IOException, MessagingException {
        return sendMailService.createDraftMessage("dpqls0625@gmail.com","dpqls0625@gmail.com", "초안 메시지 샘플", "이건 메시지 메시지");
    }

    @GetMapping("/sendEmail")
    public Message sendEmail() throws GeneralSecurityException, IOException, MessagingException {
        return sendMailService.sendEmail("dpqls0625@gmail.com","dpqls0625@gmail.com","메일보내기","매일 가나?");
    }
}
