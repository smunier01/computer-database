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

	public abstract T find(Long id);

	public abstract T create(T obj);

	public abstract T update(T obj);

	public abstract void delete(T obj);

	public abstract ArrayList<T> findAll();

	public abstract ArrayList<T> findAll(int start, int nb);

	public void closeAll(AutoCloseable... ressources) {
		for (AutoCloseable ressource : ressources) {
			try {
				ressource.close();
			} catch (Exception e) {
				logger.error("couldn't close ressource : " + ressource.toString());
			}
		}
	}
}
