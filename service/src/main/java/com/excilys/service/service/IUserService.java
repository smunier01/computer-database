package com.excilys.service.service;


import com.excilys.core.model.User;

import java.util.List;

public interface IUserService {
	List<User> listAllUser();

	User find(Integer id);
    User findByName(String username);
    
    User edit(User user);

    User create(User user);
    
    void remove(int id);
    
    void defaultValues();
}
