package com.app.MyAppBackend.repositories.link;

import com.app.MyAppBackend.model.entities.Link;
import com.app.MyAppBackend.model.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findByMyUser(MyUser myUser);
}
