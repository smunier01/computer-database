package com.excilys.service.user;


import java.util.List;

import com.excilys.core.model.User;

public interface IUserService {
	List<User> listAllUser();
	
    User findById(int id);

    User findByName(String username);

    User create(User user);

    void defaultValues();
}
