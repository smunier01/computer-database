package com.excilys.cdb.model;

/**
 * Company Class
 * 
 * @author excilys
 */
public class Company {
	
	/**
	 * id of the company
	 */
	private Long id;
	
	/**
	 * name of the company
	 */
	private String name;
	
	/**
	 * 
	 */
	public Company() {
		this.id = 0L;
		this.name = ""; 
	}
	
	public Company(Long id, String name) {
		this.id = id;
		this.name = name == null ? "" : name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return id + " " + name;
	}
}
