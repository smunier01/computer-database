package com.excilys.cdb.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * class in charge of the CLI menu.
 * 
 * @author excilys
 */
public class Menu {

	/**
	 * possible options in the menu
	 */
	private static ArrayList<String> options;

	/**
	 * 
	 */
	private ComputerDAO computerDAO = null;

	/**
	 * 
	 */
	private CompanyDAO companyDAO = null;

	/**
	 * formatter to parse the dates
	 */
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 
	 */
	private Scanner sc;

	public Menu(Scanner sc) {

		this.sc = sc;

		computerDAO = new ComputerDAO();
		companyDAO = new CompanyDAO();

		options = new ArrayList<>();

		options.add("List Computers");
		options.add("List companies");
		options.add("Show Computer Details");
		options.add("Create Computer");
		options.add("Update Computer");
		options.add("Delete Computer");
	}

	/**
	 * display the menu with the possible options
	 */
	public void print() {
		System.out.println("Menu:");

		for (int i = 0; i < options.size(); i++) {
			System.out.println((i + 1) + " : " + options.get(i));
		}
	}

	/**
	 * prompt the user to pick an answer, then route to the correct method
	 * 
	 * @return false if the user wants to quit
	 */
	public boolean pick() {

		print();

		int choice = sc.nextInt();

		return pick(choice);

	}

	/**
	 * route to the right action based on the option picked
	 * 
	 * @param choice
	 *            id of the option
	 * @return false if the user wants to quit
	 */
	public boolean pick(int choice) {

		Long computerId, companyId;
		Date introduced, discontinued;
		String name;
		Computer computer;

		boolean stop = false;

		switch (choice) {
		// display the list of computers
		case 1:
			listComputers();
			break;
			
		// display the list of companies
		case 2:
			listCompanies();
			break;
		
		// show details of an existing computer
		case 3:
			// prompt for a Long (id)
			computerId = promptForLong("id : ");

			// then show the details of the corresponding computer
			showComputerDetails(computerId);

			break;
		
		// create a new computer
		case 4:
			// prompt for a string (name)
			name = promptForString("name : ");

			// prompt for a date for the column introduced
			introduced = promptForDate("introduced date : ");

			// prompt for a date for the column discontinued
			discontinued = promptForDate("discontinued date : ");

			// prompt for a long for the id of the company
			companyId = promptForLong("company id : ");

			// create the new computer
			this.createComputer(name, introduced, discontinued, companyId);

			break;

		// update an existing computer
		case 5:

			// prompt for the the
			computerId = promptForLong("id : ");

			computer = this.computerDAO.find(computerId);

			if (computer == null) {
				break;
			}

			name = promptForString("new name (current : " + computer.getName() + " ) : ");
			introduced = promptForDate("new introduced date (current : " + computer.getIntroduced().toString() + " ) : ");
			discontinued = promptForDate(
					"new introduced date (current : " + computer.getDiscontinued().toString() + " ) : ");
			companyId = promptForLong("new company id (current : " + computer.getCompany().getId() + " ) : ");

			this.updateComputer(computerId, name, introduced, discontinued, companyId);

			break;
			
		// delete an existing computer
		case 6:
			
			computerId = promptForLong("id : ");
			
			this.deleteComputer(computerId);
			
			break;
		default:
			stop = true;
			System.out.println("quitting");
		}

		return !stop;
	}

	/**
	 * use the scanner to prompt for a Long
	 * @param s String that will be use as an indication for the prompt
	 * @return
	 */
	private Long promptForLong(String s) {
		System.out.print(s);
		
		return sc.nextLong();
	}

	/**
	 * use the scanner to prompt for a String
	 * @param s String that will be use as an indication for the prompt
	 * @return
	 */
	private String promptForString(String s) {
		System.out.print(s);
		return sc.next();
	}

	/**
	 * use the scanner to prompt for a Date
	 * @param s String that will be use as an indication for the prompt
	 * @return null if the date is not valid
	 */
	private Date promptForDate(String s) {

		System.out.print(s);
		String dateString = sc.next();

		Date date = null;

		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}
	
	/**
	 * display the list of computers using the corresponding DAO class.
	 */
	public void listComputers() {
		ArrayList<Computer> computers = this.computerDAO.findAll();

		for (Computer c : computers) {
			System.out.println(c.toString());
		}
	}

	/**
	 * display the list of companies using the corresponding DAO class.
	 */
	public void listCompanies() {
		ArrayList<Company> companies = this.companyDAO.findAll();

		for (Company c : companies) {
			System.out.println(c);
		}
	}
	
	/**
	 * create a new computer
	 * 
	 * @param name name of the computer
	 * @param introduced introduced date
	 * @param discontinued discontinued date
	 * @param companyId id of the company (0 if no company)
	 */
	public void createComputer(String name, Date introduced, Date discontinued, Long companyId) {

		// use a default company if id <= 0
		Company company = companyId <= 0 ? new Company() : this.companyDAO.find(companyId);

		Computer computer = new Computer(0L, name, introduced, discontinued, company);

		this.computerDAO.create(computer);

	}

	/**
	 * update a computer
	 * 
	 * @param id id of the computer
	 * @param name new name
	 * @param introduced new introduced date
	 * @param discontinued new discontinued date
	 * @param companyId new company id (0 if no company)
	 */
	public void updateComputer(Long id, String name, Date introduced, Date discontinued, Long companyId) {

		// use a default company if id <= 0
		Company company = companyId <= 0 ? new Company() : this.companyDAO.find(companyId);

		Computer computer = new Computer(id, name, introduced, discontinued, company);

		this.computerDAO.update(computer);

	}

	/**
	 * delete a computer
	 * 
	 * @param id id of the computer to delete
	 */
	public void deleteComputer(Long id) {
		
		// we get the computer to see if it exists
		Computer computer = this.computerDAO.find(id);

		if (computer != null) {
			this.computerDAO.delete(computer);
		}
	}

	/**
	 * show details of a computer based on its id
	 * 
	 * @param id id of the computer to show
	 */
	public void showComputerDetails(Long id) {
		Computer computer = this.computerDAO.find(id);

		if (computer != null) {
			System.out.println("Details on computer " + id + " :");
			System.out.println(computer);
		}
	}
}
