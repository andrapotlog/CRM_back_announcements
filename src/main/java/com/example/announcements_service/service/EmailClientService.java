package com.example.announcements_service.service;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public interface EmailClientService {
    //public void sendSimpleMessage(String to, String subject, String text);
    public void sendEmails(List<String> toList, String subject, String text);
    public void sendEmail(String to, String subject, String text);
}
