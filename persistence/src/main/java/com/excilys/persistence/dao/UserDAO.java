package com.excilys.persistence.dao;

import com.excilys.core.model.QUser;
import com.excilys.core.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAO {

    // list of variables
    private JPAQueryFactory jpaQuery;
    private EntityManager em;
    private QUser quser = QUser.user;

    /**
     * Use to set the entityManager.
     *
     * @param entityManager to set
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
        this.jpaQuery = new JPAQueryFactory(entityManager);
    }

    /**
     * Get the list of all the users.
     *
     * @return the list of users
     */
    @Transactional(readOnly = true)
    public List<User> listAllUser() {
        return jpaQuery.selectFrom(quser).fetch();
    }

    /**
     * Find a user with is ID.
     *
     * @param id to fins the user
     * @return the user
     */
    @Transactional(readOnly = true)
    public User find(Integer id) {
        return jpaQuery.selectFrom(quser).where(quser.id.eq(id)).fetchFirst();
    }

    /**
     * Find a user with is name.
     *
     * @param username of the user we need to find
     * @return the user
     */
    @Transactional(readOnly = true)
    public User findByUserName(String username) {
        return jpaQuery.selectFrom(quser).where(quser.username.eq(username)).fetchFirst();
    }

    /**
     * Create a user int the base.
     *
     * @param user to insert
     * @return the user with the id in the base
     */
    @Transactional(readOnly = false)
    public User create(User user) {
        this.em.persist(user);
        return user;
    }

    /**
     * Update the user in the base.
     *
     * @param obj to update
     * @return the user update
     */
    @Transactional(readOnly = false)
    public User update(User obj) {
        return this.em.merge(obj);
    }

    /**
     * Delete a user in the base.
     *
     * @param id of the user to delete
     */
    @Transactional(readOnly = false)
    public void remove(int id) {
        this.em.remove(find(id));
    }

    /**
     * Remove all the database.
     */
    @Transactional(readOnly = true)
    public void empty() {
        jpaQuery.delete(quser).execute();
    }
}
