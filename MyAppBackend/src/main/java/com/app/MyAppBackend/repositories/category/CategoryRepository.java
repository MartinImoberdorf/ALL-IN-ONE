package com.app.MyAppBackend.repositories.category;

import com.app.MyAppBackend.model.entities.Category;
import com.app.MyAppBackend.model.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameAndUser(String name, MyUser user);
    List<Category> findByUser(MyUser user);
}
