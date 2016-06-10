package com.excilys.persistence.dao;

import com.excilys.core.model.Company;
import com.excilys.core.model.PageParameters;
import com.excilys.core.model.QCompany;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
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
        obj = this.em.merge(obj);
        this.em.persist(obj);
        return obj;
    }

    @Override
    public Company update(Company obj) {
        return this.em.merge(obj);
    }

    @Override
    public void delete(Company obj) {
        this.em.remove(em.merge(obj));
    }

    public long count(PageParameters page) {
        return this.jpaQuery.from(this.qcompany).where(this.qcompany.name.like(page.getSearch() + "%")).fetchCount();
    }

    @Override
    public void deleteAll(List<Long> objs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Company> findAll() {
        return this.jpaQuery.selectFrom(this.qcompany).fetch();
    }

    public List<Company> findAll(PageParameters page) {
        return this.jpaQuery.selectFrom(this.qcompany).offset(page.getSize() * page.getPageNumber()).limit(page.getSize()).fetch();
    }

    @Override
    public long count() throws DAOException {
        return this.jpaQuery.from(this.qcompany).fetchCount();
    }

    @Override
    public List<String> findAutocompleteMatches(String entry) {
        return this.jpaQuery
                .select(this.qcompany.name)
                .distinct()
                .from(this.qcompany)
                .where(this.qcompany.name.like("%"+entry+"%"))
                .fetch();
    }
}
