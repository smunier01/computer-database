package com.excilys.cdb.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
 * @param <T>
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
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
	 * @param choice id of the option
	 * @return false if the user wants to quit
	 */
	public boolean pick(int choice) {

		Long computerId, companyId;
		LocalDate introduced, discontinued;
		String name;
		Computer computer;
		int start;

		boolean stop = false;

		switch (choice) {
		// display the list of computers
		case 1:
			
			start = 0;
			
			while (listComputers(start, 20)) {
				System.out.println("quit (0), next(1)");
				
				Long a = null;
				
				while ((a = promptForLong(":")) < 0) {
					System.out.println("invalid choice");
				}
				
				if (a == 0) {
					break;
				} else if (a == 1) {
					start += 20;
				} else if (a == 2 && start >= 20) {
					start -= 20;
				}
				
			}
			
			break;
			
		// display the list of companies
		case 2:
			start = 0;
			
			// loop for pagination
			while (listCompanies(start, 20)) {
				System.out.println("quit (0), next(1)");
				
				Long a = null;
				
				while ((a = promptForLong(":")) < 0) {
					System.out.println("invalid choice");
				}
				
				if (a == 0) {
					break;
				} else if (a == 1) {
					start += 20;
				} else if (a == 2 && start >= 20) {
					start -= 20;
				}

			}
			
			break;
		
		// show details of an existing computer
		case 3:
			
			// prompt for a Long (id)
			while ((computerId = promptForLong("id : ")) <= 0) {
				System.out.println("invalid id");
			}

			// then show the details of the corresponding computer
			showComputerDetails(computerId);

			break;
		
		// create a new computer
		case 4:
			// prompt for a string (name)
			while((name = promptForString("name : ")).equals("")) {
				System.out.println("invalid name");
			};

			// prompt for a date for the column introduced
			while ((introduced = promptForDate("introduced date (format yyyy-MM-dd) : ")) == null) {
				System.out.println("invalid date");
			}

			// prompt for a date for the column discontinued
			while ((discontinued = promptForDate("discontinued date (format yyyy-MM-dd) : ")) == null) {
				System.out.println("invalid date");
			}

			// prompt for a long for the id of the company
			while ((companyId = promptForLong("company id : ")) <= -1) {
				System.out.println("invalid id");
			}

			// create the new computer
			this.createComputer(name, introduced, discontinued, companyId);

			break;

		// update an existing computer
		case 5:

			// prompt for the computer id
			while ((computerId = promptForLong("id : ")) <= 0) {
				System.out.println("invalid id");
			}

			computer = this.computerDAO.find(computerId);

			if (computer == null) {
				break;
			}

			String tmpPromptName = "new name (current : " + computer.getName() + " ) : ";
			String tmpPromptIntro = "new introduced date (current : " + computer.getIntroduced().toString() + " ) : ";
			String tmpPromptDisco = "new introduced date (current : " + computer.getDiscontinued().toString() + " ) : ";
			String tmpPromptCompaId = "new company id (current : " + computer.getCompany().getId() + " ) : ";
			
			while ((name = promptForString(tmpPromptName)) == "") {
				System.out.println("invalid name");
			}
			
			while ((introduced = promptForDate(tmpPromptIntro)) == null) {
				System.out.println("invalid date");
			}
			
			while ((discontinued = promptForDate(tmpPromptDisco)) == null) {
				System.out.println("invalid date");
			}
			
			while ((companyId = promptForLong(tmpPromptCompaId)) <= -1) {
				System.out.println("invalid id");
			}

			this.updateComputer(computerId, name, introduced, discontinued, companyId);

			break;
			
		// delete an existing computer
		case 6:
			
			while ((computerId = promptForLong("id : ")) <= 0) {
				System.out.println("invalid id");
			}
			
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
		Long result;
		
		System.out.print(s);
		
		try {
			result = sc.nextLong();
		} catch (InputMismatchException e) {
			sc.next();
			result = -1L;
		}
		
		return result;
	}

	/**
	 * use the scanner to prompt for a String
	 * @param s String that will be use as an indication for the prompt
	 * @return
	 */
	private String promptForString(String s) {
		String result;
		
		System.out.print(s);
		
		try {
			result = sc.next();
		} catch (InputMismatchException e) {
			sc.next();
			result = "";
		}
		
		return result;
	}
	
	/**
	 * use the scanner to prompt for a Date
	 * @param s String that will be use as an indication for the prompt
	 * @return null if the date is not valid
	 */
	private LocalDate promptForDate(String s) {

		System.out.print(s);
		
		String dateString = sc.next();

		LocalDate date = null;

		try {
		
			date = LocalDate.parse(dateString, formatter);

		} catch(DateTimeParseException e) {
			
			date = null;
			
		}
		
		return date;
	}
	
	/**
	 * display the list of computers using the corresponding DAO class.
	 * @param start offset to start
	 * @param nb number of elements to return
	 * @return false if offset reached the end of the data
	 */
	public boolean listComputers(int start, int nb) {
		ArrayList<Computer> computers = this.computerDAO.findAll(start, nb);

		for (Computer c : computers) {
			System.out.println(c.toString());
		}
		
		return (computers.size() == nb);
	}

	/**
	 * display the list of companies using the corresponding DAO class.
	 * @param start offset to start
	 * @param nb number of elements to return
	 * @return false if offset reached the end of the data
	 */
	public boolean listCompanies(int start, int nb) {
		ArrayList<Company> companies = this.companyDAO.findAll(start, nb);

		for (Company c : companies) {
			System.out.println(c);
		}
		
		return (companies.size() == nb);
	}
	
	/**
	 * create a new computer
	 * 
	 * @param name name of the computer
	 * @param introduced introduced date
	 * @param discontinued discontinued date
	 * @param companyId id of the company (0 if no company)
	 */
	public void createComputer(String name, LocalDate introduced, LocalDate discontinued, Long companyId) {

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
	public void updateComputer(Long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId) {

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
