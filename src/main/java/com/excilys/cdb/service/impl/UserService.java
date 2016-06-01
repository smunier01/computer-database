package com.excilys.cdb.service.impl;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public User findBySSO(String sso) {
        return this.userDAO.findByUserName(sso);
    }
}
