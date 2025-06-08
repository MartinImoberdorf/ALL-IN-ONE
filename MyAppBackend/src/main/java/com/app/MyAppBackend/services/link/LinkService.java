package com.app.MyAppBackend.services.link;

import com.app.MyAppBackend.model.dtos.addLinkDto;
import com.app.MyAppBackend.model.entities.Link;

import java.util.List;

public interface LinkService {
    public List<Link> getAllEnlaces();
    public addLinkDto addEnlace(Link link);
}
