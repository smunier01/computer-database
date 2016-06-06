package com.excilys.persistence.dao;

import com.excilys.core.model.QUser;
import com.excilys.core.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    public User findByUserName(String username) {
        return jpaQuery.selectFrom(quser).where(quser.username.eq(username)).fetchFirst();
    }

    public User create(User user) {
        this.em.persist(user);
        return user;
    }

    public void empty() {
        jpaQuery.delete(quser).execute();
    }
}
