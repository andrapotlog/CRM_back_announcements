package com.example.announcements_service.repository;

import com.example.announcements_service.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Long> {

    @Query("SELECT a FROM Announcement a " +
            "WHERE (CASE WHEN :isUser = false THEN true " +
            "ELSE (a.areaAffected = 0 OR a.areaAffected = :areaAffected) END)" +
            "ORDER BY a.creationDate DESC")
    List<Announcement> findByAreaAffectedOrNoAreaAffected(boolean isUser, @Param("areaAffected") Integer areaAffected );
}

