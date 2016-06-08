package com.excilys.service.service.impl;


import com.excilys.core.model.User;
import com.excilys.persistence.dao.UserDAO;
import com.excilys.service.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    protected PlatformTransactionManager txManager;

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

    /**
     * PostConstruct method to init the user roles.
     * <p>
     * TransactionCallbackWithoutResult is necessary in order to make sure that the context is fully instantiated
     *
     * @throws Exception
     */
    @PostConstruct
    public void initIt() throws Exception {

        TransactionTemplate tmpl = new TransactionTemplate(txManager);
        tmpl.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                // transactionnal method that put the default users in the database.
                defaultValues();
            }
        });
    }
}
