package com.excilys.persistence.dao;

import com.excilys.core.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;


@ContextConfiguration("classpath*:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    @Transactional
    public void testfind() {
        User user = userDAO.findByUserName("admin");
        assertNotNull(user);
    }

    @Test
    @Transactional
    public void testCreate() {
        User user = new User();
        user.setUsername("testCreate");
        user.setPassword("pass");
        user.setRole("admin");

        userDAO.create(user);

        //userDAO
    }

}
