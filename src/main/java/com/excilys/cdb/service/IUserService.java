package com.excilys.cdb.service;

import com.excilys.cdb.model.User;

public interface IUserService {
    User findById(int id);
    User findBySSO(String sso);
}
