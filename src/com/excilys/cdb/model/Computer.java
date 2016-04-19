package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {
	
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	/**
	 * default constructor for a computer
	 */
	public Computer() {
		this.id = 0L;
		this.name = "";
		this.introduced = LocalDate.now();
		this.discontinued = LocalDate.now();
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
	public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
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

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
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
