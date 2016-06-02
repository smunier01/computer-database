package com.excilys.cdb.service;

import com.excilys.cdb.model.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    User findById(int id);
    User findByName(String username);
    User create(User user);
    void defaultValues();
}
