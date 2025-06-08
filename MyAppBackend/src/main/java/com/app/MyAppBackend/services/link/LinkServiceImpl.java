package com.app.MyAppBackend.services.link;

import com.app.MyAppBackend.model.entities.Category;
import com.app.MyAppBackend.model.entities.Link;
import com.app.MyAppBackend.model.entities.MyUser;
import com.app.MyAppBackend.repositories.category.CategoryRepository;
import com.app.MyAppBackend.repositories.link.LinkRepository;
import com.app.MyAppBackend.repositories.user.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MyUserRepository myUserRepository;


    @Override
    public List<Link> getAllEnlaces() {
        return repository.findAll();
    }

    @Override
    public Link addEnlace(Link link) {
        return repository.save(link);
    }
}
