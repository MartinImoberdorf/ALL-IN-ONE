package com.app.MyAppBackend.services.link;

import com.app.MyAppBackend.model.entities.Category;
import com.app.MyAppBackend.model.entities.Link;
import com.app.MyAppBackend.model.entities.User;
import com.app.MyAppBackend.repositories.category.CategoryRepository;
import com.app.MyAppBackend.repositories.link.LinkRepository;
import com.app.MyAppBackend.repositories.user.UserRepository;
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
    private UserRepository userRepository;


    @Override
    public List<Link> getAllEnlaces() {
        return repository.findAll();
    }

    @Override
    public Link addEnlace(Link link) {
        // Verificar si la categoría existe
        Category category = link.getCategory();
        if (category != null) {
            if (category.getId() == null || !categoryRepository.existsById(category.getId())) {
                category = categoryRepository.save(category);
            }
            link.setCategory(category);
        }

        // Verificar si el usuario existe por username
        User user = link.getUser();
        if (user != null) {
            if (user.getUsername() == null || !userRepository.existsByUsername(user.getUsername())) {
                throw new IllegalArgumentException("The user with the given username does not exist in the database.");
            }
        } else {
            throw new IllegalArgumentException("A user must be associated with the link.");
        }

        // Guardar el enlace
        return repository.save(link);
    }
}
