package com.excilys.service.service;


import java.util.List;

import com.excilys.core.model.User;

public interface IUserService {
	List<User> listAllUser();

	User find(Integer id);
    User findByName(String username);
    
    User edit(User user);

    User create(User user);
    
    void remove(int id);
    
    void defaultValues();
}
