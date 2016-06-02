package com.excilys.cdb.service.impl;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    protected PlatformTransactionManager txManager;

/*
    @PostConstruct
    public void initIt() throws Exception {

        TransactionTemplate tmpl = new TransactionTemplate(txManager);
        tmpl.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                defaultValues();
            }
        });
    }

*/

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public User findByName(String username) {
        return this.userDAO.findByUserName(username);
    }

    @Override
    public User create(User user) {
        return this.userDAO.create(user);
    }

    @Transactional
    @Override
    public void defaultValues() {

        this.userDAO.empty();
        this.create(new User("admin", "admin", "ADMIN"));
        this.create(new User("user", "user", "USER"));

    }
}
