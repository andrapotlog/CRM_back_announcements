package com.example.announcements_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<String> getAllUserEmails(Integer areaAffected) {
//        String url = "http://crm-user-service:8080/api/users/emails";
        String url = "http://localhost:9090/api/users/emails";
        System.out.println(url);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("location", areaAffected);

        return restTemplate.getForObject(uriBuilder.toUriString(), List.class);
    }
}
