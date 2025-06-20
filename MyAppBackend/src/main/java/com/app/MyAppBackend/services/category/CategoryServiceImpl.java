package com.app.MyAppBackend.services.category;

import com.app.MyAppBackend.model.entities.Category;
import com.app.MyAppBackend.model.entities.MyUser;
import com.app.MyAppBackend.repositories.category.CategoryRepository;
import com.app.MyAppBackend.repositories.user.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final MyUserRepository myUserRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<String> getAllCategoriesOfCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MyUser myUser = myUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return categoryRepository.findByUser(myUser).stream()
                .map(Category::getName)
                .toList();
    }
}
