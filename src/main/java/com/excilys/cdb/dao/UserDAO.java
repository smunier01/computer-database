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

    private QUser quser = QUser.user;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public void setEntityManager(EntityManager entityManager) {
        this.jpaQuery = new JPAQueryFactory(entityManager);
    }

    @SuppressWarnings("unchecked")
    public User findByUserName(String username) {
        return jpaQuery.selectFrom(quser).where(quser.username.eq(username)).fetchFirst();
    }

}
