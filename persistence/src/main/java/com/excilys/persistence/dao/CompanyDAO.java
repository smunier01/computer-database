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

    // list of variable
    private EntityManager em;
    private JPAQueryFactory jpaQuery;
    private QCompany qcompany = QCompany.company;

    /**
     * Set the entity manager.
     *
     * @param entityManager to set
     */
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

    /**
     * Return the number of element of a page.
     *
     * @param page to count the elements
     * @return the number of elements
     */
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

    /**
     * Get all the elements of a page.
     *
     * @param page to get the elements
     * @return the list of company
     */
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
                .where(this.qcompany.name.like("%" + entry + "%"))
                .fetch();
    }

    /**
     * Return a company from a company name.
     *
     * @param companyName of the company to find
     * @return the company from a name
     */
    public Company findByName(String companyName) {
        return this.jpaQuery
                .select(this.qcompany)
                .distinct()
                .from(this.qcompany)
                .where(this.qcompany.name.like(companyName))
                .fetchFirst();
    }
}
