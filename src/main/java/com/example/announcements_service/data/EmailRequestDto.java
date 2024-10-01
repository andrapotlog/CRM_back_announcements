package com.example.announcements_service.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmailRequestDto {
    private List<String> toList;
    private String subject;
    private String text;
}
