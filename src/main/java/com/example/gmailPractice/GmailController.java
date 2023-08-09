package com.example.gmailPractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class GmailController {
    @Autowired
    GmailService gmailService;

    @GetMapping("/getList")
    public Object getList() throws GeneralSecurityException, IOException {
        return gmailService.getList();
    }

    @GetMapping("/createLabel")
    public Object createLabel() throws GeneralSecurityException, IOException {
        return gmailService.createLabel();
    }

    @GetMapping("/getLabel")
    public Object getLabel() throws GeneralSecurityException, IOException {
        return gmailService.getLabel();
    }

    @GetMapping("/delLabel")
    public Object delLabel() throws GeneralSecurityException, IOException {
        return gmailService.delLabel();
    }
}
