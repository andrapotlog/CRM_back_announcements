package com.example.announcements_service.service;

import com.example.announcements_service.data.EmailRequestDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class EmailClientServiceImpl implements EmailClientService{

    @Autowired
    private RestTemplate restTemplate;

    public void sendEmails(List<String> toList, String subject, String text) {
//        String emailServiceUrl = "http://crm-email-service:8080/api/emails/send";
        String emailServiceUrl = "http://localhost:4040/api/emails/send";

        EmailRequestDto emailRequest = new EmailRequestDto();
        emailRequest.setToList(toList);
        emailRequest.setSubject(subject);
        emailRequest.setText(text);

        restTemplate.postForObject(emailServiceUrl, emailRequest, String.class);
    }

    public void sendEmail(String to, String subject, String text) {
        sendEmails(List.of(to), subject, text);
    }
}