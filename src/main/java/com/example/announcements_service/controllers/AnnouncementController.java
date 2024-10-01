package com.example.announcements_service.controllers;

import com.example.announcements_service.entity.Announcement;
import com.example.announcements_service.security.JwtConfig;
import com.example.announcements_service.service.AnnouncementService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin(origins = "http://localhost:4200")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    private final JwtConfig jwtConfig;

    @Autowired
    public AnnouncementController(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @GetMapping
    public ResponseEntity<?> getAllAnnouncements(@RequestParam(required = false) Integer location, @RequestHeader("Authorization") String authorizationHeader) {

        try {
            String token = authorizationHeader.replace("Bearer ", "");

            Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token).getBody();
            Long userId = claims.get("userId", Long.class);
            Integer locationId = claims.get("location", Integer.class);
            List<String> userRoles = claims.get("roles", List.class);

            if(userId == null) return new ResponseEntity<>("Invalid user", HttpStatus.BAD_REQUEST);
            if(userRoles == null) return new ResponseEntity<>("Invalid roles", HttpStatus.BAD_REQUEST);

            try{
                List<Announcement> announcements = announcementService.getAllAnnouncements(userRoles.contains("ROLE_USER"), locationId != null ? locationId : location);
                return new ResponseEntity<>(announcements, HttpStatus.OK);
            } catch (Exception exception) {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception exception) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Long id) {
        Optional<Announcement> announcement = announcementService.getAnnouncementById(id);
        return announcement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createAnnouncement(@RequestBody Announcement announcement,
                                           @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");

            Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token).getBody();
            Long userId = claims.get("userId", Long.class);

            if(userId == null) throw new RuntimeException("Invalid user");

            try{
                announcement.setAuthorId(userId);
                announcement.setCategory(" ");

                announcementService.createAnnouncement(announcement);
                System.out.println(announcement.getTitle());
                announcementService.sendAnnouncementToAllUsers(announcement.getAreaAffected(), announcement.getTitle(), announcement.getContent());

                return ResponseEntity.ok("Announcement created successfully");
            } catch (Exception exception) {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception exception) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }
}
