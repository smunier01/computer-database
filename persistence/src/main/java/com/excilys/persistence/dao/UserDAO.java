package com.excilys.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.core.model.Computer;
import com.excilys.core.model.QUser;
import com.excilys.core.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class UserDAO {

    private JPAQueryFactory jpaQuery;

    private EntityManager em;

    private QUser quser = QUser.user;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
        this.jpaQuery = new JPAQueryFactory(entityManager);
    }
    
    @Transactional(readOnly = true)
    public List<User> listAllUser(){
    	return jpaQuery.selectFrom(quser).fetch();
    }
    
    @Transactional(readOnly = true)
    public User find(Integer id) {
        return jpaQuery.selectFrom(quser).where(quser.id.eq(id)).fetchFirst();
    }
    
    @Transactional(readOnly = true)
    public User findByUserName(String username) {
        return jpaQuery.selectFrom(quser).where(quser.username.eq(username)).fetchFirst();
    }

    @Transactional(readOnly = false)
    public User create(User user) {
        this.em.persist(user);
        return user;
    }

    @Transactional(readOnly = false)
    public User update(User obj) {
        return this.em.merge(obj);
    }
    
    @Transactional(readOnly = false)
    public void remove(int id) {
    	this.em.remove(find(id));
    }
    
    @Transactional(readOnly = true)
    public void empty() {
        jpaQuery.delete(quser).execute();
    }
}
