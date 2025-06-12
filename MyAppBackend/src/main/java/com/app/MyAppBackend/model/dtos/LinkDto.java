package com.app.MyAppBackend.model.dtos;

import lombok.Data;

@Data
public class LinkDto {
    private Long id;
    private String title;
    private String link;
    private String description;
    private String categoryName;
    private String username;
}
