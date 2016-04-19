package com.excilys.cdb.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.excilys.cdb.jdbc.ConnectionMySQL;

/**
 * abstract class describing methods of a dao object
 * 
 * @author excilys
 *
 * @param <T>
 */
public abstract class DAO<T> {

	public Connection connect = ConnectionMySQL.getInstance();
	
	public abstract T find(Long id);
	
	public abstract T create(T obj);
	
	public abstract T update(T obj);
	
	public abstract void delete(T obj);
	
	public abstract ArrayList<T> findAll();
}
