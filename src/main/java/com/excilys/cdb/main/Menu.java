package com.excilys.cdb.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceException;

/**
 * class in charge of the CLI menu.
 *
 * @author excilys
 */
public class Menu {

	private static final int MAX_PER_PAGES = 20;

	private static ArrayList<String> options;

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private Scanner sc = null;

	private final ComputerService computerService;

	private final CompanyService companyService;

	public Menu(final Scanner sc) {

		this.sc = sc;
		computerService = ComputerService.getInstance();
		companyService = CompanyService.getInstance();

		Menu.options = new ArrayList<>();

		Menu.options.add("List Computers");
		Menu.options.add("List companies");
		Menu.options.add("Show Computer Details");
		Menu.options.add("Create Computer");
		Menu.options.add("Update Computer");
		Menu.options.add("Delete Computer");
		Menu.options.add("quit");
	}

	/**
	 * display the menu with all the possible options
	 */
	public void printMenu() {
		System.out.println("Menu:");

		for (int i = 0; i < Menu.options.size(); i++) {
			System.out.println((i + 1) + " : " + Menu.options.get(i));
		}
	}

	/**
	 * print the menu, prompt the user to pick an answer, then route to the
	 * correct method
	 *
	 * @return false if the user wants to quit
	 */
	public boolean pick() {

		printMenu();

		int choice = 0;

		while ((choice = Math.toIntExact(promptForLong(":"))) < 0) {
			System.out.println("invalid choice");
		}

		return this.pick(choice);

	}

	/**
	 * route to the right action based on the option picked
	 *
	 * @param choice
	 *            id of the option
	 * @return false if the user wants to quit
	 */
	public boolean pick(final int choice) {

		Long computerId, companyId;
		LocalDate introduced, discontinued;
		String name;
		Computer computer = null;
		int start;

		boolean stop = false;

		switch (choice) {
		// display the list of computers
		case 1:

			start = 0;

			// loop for pagination
			while (listComputers(start, Menu.MAX_PER_PAGES)) {
				System.out.println("quit (0), next(1), previous(2)");

				Long a = null;

				while ((a = promptForLong(":")) < 0) {
					System.out.println("invalid choice");
				}

				if (a == 0) {
					break;
				} else if (a == 1) {
					start += Menu.MAX_PER_PAGES;
				} else if ((a == 2) && (start >= Menu.MAX_PER_PAGES)) {
					start -= Menu.MAX_PER_PAGES;
				}

			}

			break;

		// display the list of companies
		case 2:
			start = 0;

			// loop for pagination
			while (listCompanies(start, Menu.MAX_PER_PAGES)) {
				System.out.println("quit (0), next(1), previous(2)");

				Long a = null;

				while ((a = promptForLong(":")) < 0) {
					System.out.println("invalid choice");
				}

				if (a == 0) {
					break;
				} else if (a == 1) {
					start += Menu.MAX_PER_PAGES;
				} else if ((a == 2) && (start >= Menu.MAX_PER_PAGES)) {
					start -= Menu.MAX_PER_PAGES;
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
			while ((name = promptForString("name : ")).equals("")) {
				System.out.println("invalid name");
			}
			;

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
			try {
				computerService.createComputer(name, introduced, discontinued, companyId);
			} catch (final ServiceException e) {
				System.out.println("Could not create Computer :(");
			}

			break;

		// update an existing computer
		case 5:

			// prompt for the computer id
			while ((computerId = promptForLong("id : ")) <= 0) {
				System.out.println("invalid id");
			}

			try {
				computer = computerService.getComputer(computerId);
			} catch (final ServiceException e) {
				System.out.println("Error while retrieving computer of id " + computerId);
				break;
			}

			if (computer == null) {
				System.out.println("Computer of id " + computerId + " doesn't exists");
				break;
			}

			final String tmpPromptName = "new name (current : " + computer.getName() + " ) : ";
			final String tmpPromptIntro = "new introduced date (current : " + computer.getIntroduced().toString()
					+ " ) : ";
			final String tmpPromptDisco = "new introduced date (current : " + computer.getDiscontinued().toString()
					+ " ) : ";
			final String tmpPromptCompaId = "new company id (current : " + computer.getCompany().getId() + " ) : ";

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

			try {
				computerService.updateComputer(computerId, name, introduced, discontinued, companyId);
			} catch (final ServiceException e) {
				System.out.println("Error updating computer.");
			}

			break;

		// delete an existing computer
		case 6:

			while ((computerId = promptForLong("id : ")) <= 0) {
				System.out.println("invalid id");
			}

			try {
				computerService.deleteComputer(computerId);
			} catch (final ServiceException e) {
				System.out.println("Error updating computer.");
			}

			break;

		// quit
		default:
			stop = true;
			System.out.println("quitting");
		}

		return !stop;
	}

	/**
	 * use the scanner to prompt for a Long
	 *
	 * @param s
	 *            String that will be use as an indication for the prompt
	 * @return
	 */
	private Long promptForLong(final String s) {
		Long result;

		System.out.print(s);

		try {
			result = sc.nextLong();
		} catch (final InputMismatchException e) {
			sc.next();
			result = -1L;
		}

		return result;
	}

	/**
	 * use the scanner to prompt for a String
	 *
	 * @param s
	 *            String that will be use as an indication for the prompt
	 * @return
	 */
	private String promptForString(final String s) {
		String result;

		System.out.print(s);

		try {
			result = sc.next();
		} catch (final InputMismatchException e) {
			sc.next();
			result = "";
		}

		return result;
	}

	/**
	 * use the scanner to prompt for a Date
	 *
	 * @param s
	 *            String that will be use as an indication for the prompt
	 * @return null if the date is not valid, LocalDate.MIN if empty date
	 */
	private LocalDate promptForDate(final String s) {

		System.out.print(s);

		final String dateString = sc.next();

		LocalDate date = null;

		if ("".equals(dateString)) {
			date = LocalDate.MIN;
		} else {
			try {
				date = LocalDate.parse(dateString, formatter);
			} catch (final DateTimeParseException e) {
				date = null;
			}
		}

		return date;
	}

	/**
	 * display the list of computers.
	 *
	 * @param start
	 *            offset to start
	 * @param nb
	 *            number of elements to return
	 * @return false if offset reached the end of the data
	 */
	public boolean listComputers(final int start, final int nb) {
		List<Computer> computers = null;

		try {
			computers = computerService.getComputers(start, nb);
		} catch (final ServiceException e) {
			System.out.println("Error retrieving list of computer.");
			return false;
		}

		for (final Computer c : computers) {
			System.out.println(c.toString());
		}

		return (computers.size() == nb);
	}

	/**
	 * display the list of companies
	 *
	 * @param start
	 *            offset to start
	 * @param nb
	 *            number of elements to return
	 * @return false if offset reached the end of the data
	 */
	public boolean listCompanies(final int start, final int nb) {
		List<Company> companies = null;

		try {
			companies = companyService.getCompanies(start, nb);
		} catch (final ServiceException e) {
			System.out.println("Error retrieving list of companies.");
			return false;
		}

		for (final Company c : companies) {
			System.out.println(c);
		}

		return (companies.size() == nb);
	}

	/**
	 * show details of a computer based on its id
	 *
	 * @param id
	 *            id of the computer to show
	 */
	public void showComputerDetails(final Long id) {
		Computer computer = null;
		try {
			computer = computerService.getComputer(id);
		} catch (final ServiceException e) {
			System.out.println("Error retrieving computer.");
		}

		if (computer != null) {
			System.out.println("Details on computer " + id + " :");
			System.out.println(computer);
		} else {
			System.out.println("Computer of id " + id + " doesn't exist");
		}
	}
}
