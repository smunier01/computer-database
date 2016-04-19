package com.excilys.cdb.model;

import java.util.Date;

public class Computer {
	
	private Long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;
	
	/**
	 * default constructor for a computer
	 */
	public Computer() {
		this.id = 0L;
		this.name = "";
		this.introduced = new Date();
		this.discontinued = new Date();
		this.company = new Company();
	}
	
	/**
	 * constructor for a computer
	 * 
	 * @param id id
	 * @param name name
	 * @param introduced introduced date
	 * @param discontinued discontinued date
	 * @param company company (with id = 0 if no company)
	 */
	public Computer(Long id, String name, Date introduced, Date discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public String toString() {
		
		String intro = introduced == null ? "null" : introduced.toString();
		String discon = discontinued == null ? "null" : discontinued.toString();
		
		return id + " " + name + " " + intro + " " + discon + " " + company.toString();
	}
	
	
}
