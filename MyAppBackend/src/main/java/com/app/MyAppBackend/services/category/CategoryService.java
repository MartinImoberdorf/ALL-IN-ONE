package com.app.MyAppBackend.services.category;


import com.app.MyAppBackend.model.entities.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategories();
    public Category addCategory(Category category);
}
