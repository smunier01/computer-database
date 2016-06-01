package com.excilys.cdb.dao;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.PageParameters;
import com.excilys.cdb.model.QCompany;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Singleton for the CompanyDAO.
 * <p>
 * implements all the CRUD operations defined in DAO<>.
 *
 * @author simon
 */
@Repository
public class CompanyDAO implements DAO<Company> {

    private EntityManager em;

    private JPAQueryFactory jpaQuery;

    private QCompany qcompany = QCompany.company;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
        this.jpaQuery = new JPAQueryFactory(entityManager);
    }

    @Override
    public Company find(Long id) {
        return this.jpaQuery.selectFrom(this.qcompany).where(this.qcompany.id.eq(id)).fetchFirst();
    }

    @Override
    public Company create(Company obj) {
        this.em.persist(obj);
        return obj;
    }

    @Override
    public Company update(Company obj) {
        return this.em.merge(obj);
    }

    @Override
    public void delete(Company obj) {
        this.em.remove(obj);
    }

    @Override
    public long count(PageParameters page) {
        return this.jpaQuery.from().fetchCount();
    }

    @Override
    public void deleteAll(List<Long> objs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Company> findAll() {
        return this.jpaQuery.selectFrom(this.qcompany).fetch();
    }

    @Override
    public List<Company> findAll(PageParameters page) {
        return this.jpaQuery.selectFrom(this.qcompany).offset(page.getSize() * page.getPageNumber()).limit(page.getSize()).fetch();
    }

    @Override
    public long count() throws DAOException {
        return this.jpaQuery.from(this.qcompany).fetchCount();
    }

}
