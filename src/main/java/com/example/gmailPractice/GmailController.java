package com.example.gmailPractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class GmailController {
    @Autowired
    GmailService gmailService;

    @GetMapping("/sample")
    public Object sample() throws GeneralSecurityException, IOException {
        return gmailService.quickStart();
    }
}
