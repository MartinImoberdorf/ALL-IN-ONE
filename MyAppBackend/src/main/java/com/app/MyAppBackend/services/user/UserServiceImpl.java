package com.app.MyAppBackend.services.user;

import com.app.MyAppBackend.model.entities.User;
import com.app.MyAppBackend.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Override
    public User addUser(User user) {
        return repository.save(user);
    }
}
