package com.excilys.service.user.impl;

import com.excilys.core.model.User;
import com.excilys.persistence.dao.UserDAO;
import com.excilys.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	protected PlatformTransactionManager txManager;

	public List<User> listAllUser() {
		return userDAO.listAllUser();
	}

	@Override
	public User find(Integer id) {
		return userDAO.find(id);
	}

	@Override
	public User findByName(String username) {
		return this.userDAO.findByUserName(username);
	}

	@Override
	public User create(User user) {
		return this.userDAO.create(user);
	}
	
	@Override
	public User edit(User user){
		return userDAO.update(user);
	}

	@Override
	public void remove(int id) {
		userDAO.remove(id);
	}
	
	@Transactional
	@Override
	public void defaultValues() {
		if (findByName("admin") == null) {
			this.create(new User("admin", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918", "ADMIN"));
		}
		if (findByName("user") == null) {
			this.create(new User("user", "04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb", "USER"));
		}
	}

	/**
	 * PostConstruct method to init the user roles.
	 * <p>
	 * TransactionCallbackWithoutResult is necessary in order to make sure that
	 * the spring context is fully instantiated.
	 *
	 * @throws Exception
	 */
	@PostConstruct
	public void initIt() throws Exception {

		TransactionTemplate tmpl = new TransactionTemplate(txManager);
		tmpl.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				// transactionnal method that put the default users in the
				// database.
				defaultValues();
			}
		});
	}
}
