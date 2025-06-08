package com.app.MyAppBackend.model.dtos;

import lombok.Data;

@Data
public class addLinkDto {
    private String link;
    private String description;
    private String categoryName;
    private String username;
}
