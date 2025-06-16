package com.app.MyAppBackend.services.link;

import com.app.MyAppBackend.model.dtos.LinkDto;
import com.app.MyAppBackend.model.entities.Category;
import com.app.MyAppBackend.model.entities.Link;
import com.app.MyAppBackend.model.entities.MyUser;
import com.app.MyAppBackend.repositories.category.CategoryRepository;
import com.app.MyAppBackend.repositories.link.LinkRepository;
import com.app.MyAppBackend.repositories.user.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;
    private final CategoryRepository categoryRepository;
    private final MyUserRepository myUserRepository;

    @Override
    public List<LinkDto> getAllLinks() {
        return linkRepository.findAll().stream().map(link -> {
            LinkDto dto = new LinkDto();
            dto.setId(link.getId());
            dto.setTitle(link.getTitle());
            dto.setLink(link.getLink());
            dto.setDescription(link.getDescription());
            dto.setCategoryName(link.getCategory().getName());
            dto.setUsername(link.getMyUser().getUsername());
            return dto;
        }).toList();
    }

    @Override
    public LinkDto addLink(Link link) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MyUser myUser = myUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Category category = categoryRepository.findByName(link.getCategory().getName())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        link.setMyUser(myUser);
        link.setCategory(category);

        Link saved = linkRepository.save(link);

        LinkDto dto = new LinkDto();
        dto.setId(saved.getId());
        dto.setTitle(saved.getTitle());
        dto.setLink(saved.getLink());
        dto.setDescription(saved.getDescription());
        dto.setCategoryName(saved.getCategory().getName());
        dto.setUsername(saved.getMyUser().getUsername());

        return dto;
    }

    @Override
    public LinkDto removeLink(Long id) {
        Link buscado = linkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Link not found with id: " + id));
        linkRepository.delete(buscado);

        LinkDto dto = new LinkDto();
        dto.setId(buscado.getId());
        dto.setTitle(buscado.getTitle());
        dto.setLink(buscado.getLink());
        dto.setDescription(buscado.getDescription());
        dto.setCategoryName(buscado.getCategory().getName());
        dto.setUsername(buscado.getMyUser().getUsername());
        return dto;
    }

    @Override
    public List<LinkDto> getLinksActualUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MyUser myUser = myUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return linkRepository.findByMyUser(myUser).stream().map(link -> {
            LinkDto dto = new LinkDto();
            dto.setId(link.getId());
            dto.setTitle(link.getTitle());
            dto.setLink(link.getLink());
            dto.setDescription(link.getDescription());
            dto.setCategoryName(link.getCategory().getName());
            dto.setUsername(link.getMyUser().getUsername());
            return dto;
        }).toList();
    }

}
