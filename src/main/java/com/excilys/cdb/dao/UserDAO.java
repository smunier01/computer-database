package com.excilys.cdb.dao;

import com.excilys.cdb.model.QUser;
import com.excilys.cdb.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Repository
public class UserDAO {

    private JPAQueryFactory jpaQuery;

    private EntityManager em;

    private QUser quser = QUser.user;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
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