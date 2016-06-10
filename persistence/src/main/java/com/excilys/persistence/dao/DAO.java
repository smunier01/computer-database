package com.excilys.persistence.dao;

import com.excilys.core.model.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * abstract class describing methods of a dao object.
 *
 * @param <T>
 * @author simon
 */
public interface DAO<T> {

    Logger LOGGER = LoggerFactory.getLogger(DAO.class);

    /**
     * find an object by its id.
     *
     * @param id of the object
     * @return instance of the object found
     */
    T find(Long id);

    /**
     * create a new object.
     *
     * @param obj object to create
     * @return instance of the object created
     */
    T create(T obj);

    /**
     * update an object.
     *
     * @param obj object to update
     * @return instance of the object updated
     */
    T update(T obj);

    /**
     * remove an object.
     *
     * @param obj object to remove
     */
    void delete(T obj);

    /**
     * remove multiple object by their IDs.
     *
     * @param objs list of the object ids to remove
     */
    void deleteAll(List<Long> objs);

    /**
     * return all object.
     *
     * @return list containing the objects
     */
    List<T> findAll();

    /**
     * return all object with an offset and a limit.
     *
     * @param params page parameters
     * @return the list of object
     */
    List<T> findAll(PageParameters params);

    /**
     * count the number of object in the table.
     *
     * @return number of object
     */
    long count();

    /**
     * Count number of object using a page parameters.
     *
     * @param params parameters for the query.
     * @return number of object
     */
    long count(PageParameters params);

    /**
     * Find the autoCompleteMatches.
     *
     * @param entry ti find
     * @return the list of autocompleteMatches
     */
    List<String> findAutocompleteMatches(String entry);
}
