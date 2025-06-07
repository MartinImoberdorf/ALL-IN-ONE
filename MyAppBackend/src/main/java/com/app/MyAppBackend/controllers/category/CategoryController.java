package com.app.MyAppBackend.controllers.category;

import com.app.MyAppBackend.model.entities.Category;
import com.app.MyAppBackend.services.category.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping
    private List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping
    private Category addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }
}
