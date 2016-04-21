package com.excilys.cdb.dao;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * @param id of the object
	 * @return 
	 */
	public abstract T find(Long id);

	/**
	 * create a new object
	 * @param obj object to create
	 * @return
	 */
	public abstract T create(T obj);

	/**
	 * update an object
	 * @param obj object to update
	 * @return
	 */
	public abstract T update(T obj);

	/**
	 * remove an object
	 * @param obj object to remove
	 */
	public abstract void delete(T obj);

	/**
	 * return all object
	 * @return arraylist containing the objects
	 */
	public abstract ArrayList<T> findAll();
	
	/**
	 * return all object with an offset and a limit
	 * @param start offset
	 * @param nb number of object to return
	 * @return arraylist containing the objects
	 */
	public abstract ArrayList<T> findAll(int start, int nb);

	/**
	 * close the list of resources given
	 * @param resources resources to close
	 */
	public void closeAll(AutoCloseable... resources) {
		for (AutoCloseable resource : resources) {
			try {
				resource.close();
			} catch (Exception e) {
				logger.error("couldn't close resource : " + resource.toString());
			}
		}
	}
}
