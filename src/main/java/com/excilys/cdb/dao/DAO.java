package com.excilys.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.DAOException;

/**
 * abstract class describing methods of a dao object
 *
 * @author excilys
 *
 * @param <T>
 */
public abstract class DAO<T> {

    final static Logger logger = LoggerFactory.getLogger(DAO.class);

    /**
     * find an object by its id
     *
     * @param id
     *            of the object
     * @return
     */
    public abstract T find(Long id) throws DAOException;

    /**
     * create a new object
     *
     * @param obj
     *            object to create
     * @return
     */
    public abstract T create(T obj) throws DAOException;

    /**
     * update an object
     *
     * @param obj
     *            object to update
     * @return
     */
    public abstract T update(T obj) throws DAOException;

    /**
     * remove an object
     *
     * @param obj
     *            object to remove
     */
    public abstract void delete(T obj) throws DAOException;

    /**
     * return all object
     *
     * @return arraylist containing the objects
     */
    public abstract List<T> findAll() throws DAOException;

    /**
     * return all object with an offset and a limit
     *
     * @param start
     *            offset
     * @param nb
     *            number of object to return
     * @return arraylist containing the objects
     */
    public abstract List<T> findAll(int start, int nb) throws DAOException;

    /**
     * count the number of object in the table
     *
     * @return number of object as long
     * @throws DAOException
     */
    public abstract long count() throws DAOException;

    /**
     * close the list of resources given
     *
     * @param resources
     *            resources to close
     */
    protected void closeAll(final AutoCloseable... resources) {
        for (final AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (final Exception e) {
                    DAO.logger.error("couldn't close resource : " + resource.toString());
                }
            }
        }
    }

    protected PreparedStatement setParams(final PreparedStatement stmt, final Object... params) throws SQLException {

        int cnt = 0;

        for (final Object o : params) {
            stmt.setObject(++cnt, o);
        }

        return stmt;

    }
}
