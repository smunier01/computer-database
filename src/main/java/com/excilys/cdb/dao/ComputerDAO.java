package com.excilys.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.mapper.LocalDateMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.PageParameters;
import com.excilys.cdb.model.PageParameters.Direction;
import com.excilys.cdb.model.PageParameters.Order;
import com.excilys.cdb.model.QCompany;
import com.excilys.cdb.model.QComputer;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * Singleton for the ComputerDAO.
 *
 * implements all the CRUD operations defined in DAO<>.
 *
 * @author simon
 *
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

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
        this.jpaQuery = new JPAQueryFactory(entityManager);
    }

    @Override
    public Computer find(Long id) {
        return this.jpaQuery.selectFrom(this.qcomputer).where(this.qcomputer.id.eq(id)).fetchFirst();
    }

    @Override
    public Computer create(Computer obj) {
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
     * @param id
     *            id of the company to whom the computers to delete belong.
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
        return this.jpaQuery.selectFrom(this.qcomputer)
                .leftJoin(this.qcomputer.company, this.qcompany)
                .where(this.qcomputer.name.like(page.getSearch() + "%"))
                .orderBy(ComputerDAO.getOrderMethod(page.getOrder(), page.getDirection()))
                .offset(page.getSize() * page.getPageNumber())
                .limit(page.getSize())
                .fetch();
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
