package com.example.announcements_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

enum Status {
    active,
    inactive,
    undefined,
    done
}

@Entity
@Table(name="announcements")
@Getter
@Setter
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name="content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status = Status.undefined;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name="category")
    private String category;

    @Column(name = "area_affected")
    private String areaAffected;
}

