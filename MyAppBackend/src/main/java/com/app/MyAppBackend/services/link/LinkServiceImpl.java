package com.app.MyAppBackend.services.link;

import com.app.MyAppBackend.model.dtos.addLinkDto;
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
    public List<Link> getAllEnlaces() {
        return linkRepository.findAll();
    }

    @Override
    public addLinkDto addEnlace(Link link) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MyUser myUser = myUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Category category = categoryRepository.findByName(link.getCategory().getName())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        link.setMyUser(myUser);
        link.setCategory(category);

        Link saved = linkRepository.save(link);

        addLinkDto dto = new addLinkDto();
        dto.setLink(saved.getLink());
        dto.setDescription(saved.getDescription());
        dto.setCategoryName(saved.getCategory().getName());
        dto.setUsername(saved.getMyUser().getUsername());

        return dto;
    }
}
