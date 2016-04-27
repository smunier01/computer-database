package com.excilys.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.util.PageParameters;

/**
 * abstract class describing methods of a dao object.
 *
 * @author excilys
 *
 * @param <T>
 */
public abstract class DAO<T> {

    static final Logger LOGGER = LoggerFactory.getLogger(DAO.class);

    /**
     * find an object by its id.
     *
     * @param id
     *            of the object
     * @return instance of the object found
     * @throws DAOException
     *             exception
     */
    public abstract T find(Long id) throws DAOException;

    /**
     * create a new object.
     *
     * @param obj
     *            object to create
     * @return instance of the object created
     * @throws DAOException
     *             exception
     */
    public abstract T create(T obj) throws DAOException;

    /**
     * update an object.
     *
     * @param obj
     *            object to update
     * @return instance of the object updated
     * @throws DAOException
     *             exception
     */
    public abstract T update(T obj) throws DAOException;

    /**
     * remove an object.
     *
     * @param obj
     *            object to remove
     * @throws DAOException
     *             exception
     */
    public abstract void delete(T obj) throws DAOException;

    /**
     * return all object.
     *
     * @return list containing the objects
     * @throws DAOException
     *             exception
     */
    public abstract List<T> findAll() throws DAOException;

    /**
     * return all object with an offset and a limit.
     *
     * @param params
     *            page parameters
     * @return the list of object
     * @throws DAOException
     *             exception
     */
    public abstract List<T> findAll(PageParameters params) throws DAOException;

    /**
     * count the number of object in the table.
     *
     * @return number of object as long
     * @throws DAOException
     *             exception
     */
    public abstract long count() throws DAOException;

    /**
     * close the list of resources given.
     *
     * @param resources
     *            resources to close
     * @throws DAOException
     *             exception
     */
    protected final void closeAll(final AutoCloseable... resources) {
        for (final AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (final Exception e) {
                    DAO.LOGGER.error("couldn't close resource : " + resource.toString());
                }
            }
        }
    }

    /**
     * add params contained in the varargs to the prepared statement.
     *
     * @param stmt
     *            prepared statement where to set the params
     * @param params
     *            varargs of Object to be added to the prepared statement
     * @return same instance of the prepared statement (for conveniance)
     * @throws SQLException
     *             exception
     */
    protected PreparedStatement setParams(final PreparedStatement stmt, final Object... params) throws SQLException {

        int cnt = 0;

        for (final Object o : params) {
            stmt.setObject(++cnt, o);
        }

        return stmt;

    }
}
