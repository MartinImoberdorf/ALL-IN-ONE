package com.app.MyAppBackend.services.link;

import com.app.MyAppBackend.model.entities.Link;

import java.util.List;

public interface LinkService {
    public List<Link> getAllEnlaces();
    public Link addEnlace(Link link);
}
