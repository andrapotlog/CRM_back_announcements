package com.example.announcements_service.service;

import com.example.announcements_service.entity.Announcement;
import com.example.announcements_service.repository.AnnouncementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private AnnouncementRepo announcementRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private EmailClientService emailClientService;

    @Override
    public List<Announcement> getAllAnnouncements(boolean isUser, Integer location) {
        return announcementRepository.findByAreaAffectedOrNoAreaAffected(isUser, location);
    }

    @Override
    public Optional<Announcement> getAnnouncementById(Long id) {
        return announcementRepository.findById(id);
    }

    @Override
    public Announcement createAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    @Override
    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }

    @Override
    public void sendAnnouncementToAllUsers(Integer areaAffected,String subject, String text) {
        List<String> userEmails = userServiceImpl.getAllUserEmails(areaAffected);
        System.out.println(userEmails.get(0));
        emailClientService.sendEmails(userEmails, subject,text);
    }
}
