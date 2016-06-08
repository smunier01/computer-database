package com.excilys.persistence.dao;

import com.excilys.core.model.Computer;
import com.excilys.core.model.PageParameters;
import com.excilys.core.model.PageParameters.Direction;
import com.excilys.core.model.PageParameters.Order;
import com.excilys.core.model.QCompany;
import com.excilys.core.model.QComputer;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

/**
 * Singleton for the ComputerDAO.
 * <p>
 * implements all the CRUD operations defined in DAO<>.
 *
 * @author simon
 */
@Repository
public class ComputerDAO implements DAO<Computer> {

    private EntityManager em;

    private JPAQueryFactory jpaQuery;

    private QComputer qcomputer = QComputer.computer;

    private QCompany qcompany = QCompany.company;

    @SuppressWarnings("rawtypes")
    public static OrderSpecifier<? extends Comparable> getOrderMethod(Order o, Direction d) {
        PathBuilder<QComputer> orderByExpression = new PathBuilder<>(QComputer.class, "computer");
        return new OrderSpecifier<>(com.querydsl.core.types.Order.ASC, orderByExpression.get(o.toString().toLowerCase(), Comparable.class));
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
        this.jpaQuery = new JPAQueryFactory(this.em);
    }

    @Override
    public Computer find(Long id) {
        return this.jpaQuery.selectFrom(this.qcomputer).where(this.qcomputer.id.eq(id)).fetchFirst();
    }

    @Override
    public Computer create(Computer obj) {
        obj = this.em.merge(obj);
        this.em.persist(obj);
        return obj;
    }

    @Override
    public Computer update(Computer obj) {
        return this.em.merge(obj);
    }

    @Override
    public void delete(Computer obj) {
        this.em.remove(obj);
    }

    /**
     * Delete computers based on their company.
     *
     * @param id id of the company to whom the computers to delete belong.
     */
    public void deleteByCompanyId(Long id) {
        this.jpaQuery.delete(this.qcomputer).where(this.qcomputer.company.id.eq(id)).execute();
    }

    @Override
    public void deleteAll(List<Long> objs) {
        this.jpaQuery.delete(this.qcomputer).where(this.qcomputer.id.in(objs)).execute();
    }

    @Override
    public List<Computer> findAll() {
        return this.jpaQuery.selectFrom(this.qcomputer)
                .leftJoin(this.qcomputer.company, this.qcompany)
                .fetch();
    }

    @Override
    public List<Computer> findAll(PageParameters page) {
        if (page.getSearch().isEmpty()) {
            return this.findAllNormal(page);
        } else {
            return this.findAllLucene(page);
        }
    }

    private List<Computer> findAllNormal(PageParameters page) {
        return this.jpaQuery
                .selectFrom(this.qcomputer)
                .leftJoin(this.qcomputer.company, this.qcompany)
                .orderBy(ComputerDAO.getOrderMethod(page.getOrder(), page.getDirection()))
                .offset(page.getSize() * page.getPageNumber())
                .limit(page.getSize())
                .fetch();
    }

    /**
     * this is more efficient when the offset is large
     */
    private List<Computer> findAllWithSubQuery(PageParameters page) {
        List<Long> ids;

        if (page.getSearch().isEmpty()) {
            ids = this.jpaQuery.select(this.qcomputer.id)
                    .from(this.qcomputer)
                    .orderBy(ComputerDAO.getOrderMethod(page.getOrder(), page.getDirection()))
                    .offset(page.getSize() * page.getPageNumber())
                    .limit(page.getSize())
                    .fetch();
        } else {
            ids = this.jpaQuery.select(this.qcomputer.id)
                    .from(this.qcomputer)
                    .where(this.qcomputer.name.like(page.getSearch() + "%"))
                    .orderBy(ComputerDAO.getOrderMethod(page.getOrder(), page.getDirection()))
                    .offset(page.getSize() * page.getPageNumber())
                    .limit(page.getSize())
                    .fetch();
        }

        return this.jpaQuery.selectFrom(this.qcomputer)
                .leftJoin(this.qcomputer.company, this.qcompany)
                .where(this.qcomputer.id.in(ids))
                .fetch();
    }

    /**
     * Use hibernate-search with lucene back-end to do the search.
     */
    @SuppressWarnings("unchecked")
    private List<Computer> findAllLucene(PageParameters page) {

        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        SearchFactory sf = fullTextEntityManager.getSearchFactory();

        QueryBuilder computerQB = sf.buildQueryBuilder().forEntity(Computer.class).get();

        org.apache.lucene.search.Query luceneQuery = computerQB.keyword()
                .onField("name")
                .andField("company.name")
                .matching(page.getSearch())
                .createQuery();

        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Computer.class);
        fullTextQuery.setFirstResult((int) (page.getSize() * page.getPageNumber()));
        fullTextQuery.setMaxResults((int) page.getSize());
        fullTextQuery.setSort(new Sort(new SortField("name", SortField.Type.STRING)));

        return fullTextQuery.getResultList();
    }

    public void buildIndex() throws InterruptedException, IOException {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        fullTextEntityManager.createIndexer().startAndWait();
    }

    @Override
    public long count(PageParameters page) {
        return this.jpaQuery.from(this.qcomputer).where(this.qcomputer.name.like(page.getSearch() + "%")).fetchCount();
    }

    @Override
    public long count() {
        return this.jpaQuery.from(this.qcomputer).fetchCount();
    }
}
