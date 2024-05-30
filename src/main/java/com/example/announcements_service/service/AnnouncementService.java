package com.example.announcements_service.service;

import com.example.announcements_service.entity.Announcement;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    public List<Announcement> getAllAnnouncements();

    public Optional<Announcement> getAnnouncementById(Long id);

    public Announcement createAnnouncement(Announcement announcement);

    public void deleteAnnouncement(Long id);
}
