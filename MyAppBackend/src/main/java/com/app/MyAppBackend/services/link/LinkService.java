package com.app.MyAppBackend.services.link;

import com.app.MyAppBackend.model.dtos.LinkDto;
import com.app.MyAppBackend.model.entities.Link;

import java.util.List;

public interface LinkService {
    public List<LinkDto> getAllLinks();
    public LinkDto addLink(Link link);
    public LinkDto removeLink(Long id);
    public List<LinkDto> getLinksActualUser();
}
