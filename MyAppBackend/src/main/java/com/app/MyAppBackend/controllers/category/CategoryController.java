package com.app.MyAppBackend.controllers.category;

import com.app.MyAppBackend.model.entities.Category;
import com.app.MyAppBackend.model.entities.MyUser;
import com.app.MyAppBackend.repositories.category.CategoryRepository;
import com.app.MyAppBackend.repositories.user.MyUserRepository;
import com.app.MyAppBackend.services.category.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private final MyUserRepository myUserRepository;
    private final CategoryRepository categoryRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    private List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category, Authentication authentication) {
        String username = authentication.getName();
        MyUser user = myUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        category.setUser(user);
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/currentUser")
    public ResponseEntity<List<String>> getAllCategoriesOfCurrentUser(){
        List<String> categories = categoryService.getAllCategoriesOfCurrentUser();
        return ResponseEntity.ok(categories);
    }
}
