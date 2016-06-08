package com.excilys.console;

import com.excilys.core.model.Company;
import com.excilys.core.model.Computer;
import com.excilys.core.model.PageParameters;
import com.excilys.service.service.ICompanyRestService;
import com.excilys.service.service.IComputerRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * class in charge of displaying the menu and calling the rest service client depending on the inputs.
 */
@Component
public class Menu {
    private static final int MAX_PER_PAGES = 20;

    private static ArrayList<String> options;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Scanner sc = null;

    @Autowired
    private IComputerRestService computerRestService;

    @Autowired
    private ICompanyRestService companyRestService;

    /**
     * default constructor for the menu.
     *
     * initialized the list of possible options.
     */
    public Menu() {
        this.sc = new Scanner(System.in);
        this.sc.useDelimiter("\\n");

        Menu.options = new ArrayList<>();

        Menu.options.add("List Computers");
        Menu.options.add("List companies");
        Menu.options.add("Show Computer Details");
        Menu.options.add("Create Computer");
        Menu.options.add("Update Computer");
        Menu.options.add("Delete Computer");
        Menu.options.add("Delete Company");
        Menu.options.add("quit");
    }

    /**
     * close the scanner.
     */
    public void close() {
        if (this.sc != null) {
            this.sc.close();
        }
    }

    /**
     * display the menu with all the possible options.
     */
    public void printMenu() {
        System.out.println("Menu:");

        for (int i = 0; i < Menu.options.size(); i++) {
            System.out.println((i + 1) + " : " + Menu.options.get(i));
        }
    }

    /**
     * print the menu, prompt the user to pick an answer, then route to the
     * correct method.
     *
     * @return false if the user wants to quit
     */
    public boolean pick() {

        this.printMenu();

        int choice;

        while ((choice = Math.toIntExact(this.promptForLong(":"))) < 0) {
            System.out.println("invalid choice");
        }

        return this.pick(choice);

    }

    /**
     * route to the right action based on the option picked.
     *
     * @param choice id of the option
     * @return false if the user wants to quit
     */
    private boolean pick(int choice) {

        Long computerId, companyId;
        LocalDate introduced, discontinued;
        String name;
        Computer computer;
        PageParameters page;

        boolean stop = false;

        switch (choice) {
            // display the list of computers
            case 1:

                page = new PageParameters.Builder().size(Menu.MAX_PER_PAGES).pageNumber(0).build();

                // loop for pagination
                while (this.listComputers(page)) {
                    System.out.println("quit (0), next(1), previous(2)");

                    Long a;

                    while ((a = this.promptForLong(":")) < 0) {
                        System.out.println("invalid choice");
                    }

                    if (a == 0) {
                        break;
                    } else if (a == 1) {
                        page.incPage();
                    } else if (a == 2) {
                        page.decPage();
                    }

                }

                break;

            // display the list of companies
            case 2:
                page = new PageParameters.Builder().size(Menu.MAX_PER_PAGES).pageNumber(0).build();

                // loop for pagination
                while (this.listCompanies(page)) {
                    System.out.println("quit (0), next(1), previous(2)");

                    Long a;

                    while ((a = this.promptForLong(":")) < 0) {
                        System.out.println("invalid choice");
                    }

                    if (a == 0) {
                        break;
                    } else if (a == 1) {
                        page.incPage();
                    } else if (a == 2) {
                        page.decPage();
                    }

                }

                break;

            // show details of an existing computer
            case 3:

                // prompt for a Long (id)
                while ((computerId = this.promptForLong("id : ")) <= 0) {
                    System.out.println("invalid id");
                }

                // then show the details of the corresponding computer
                this.showComputerDetails(computerId);

                break;

            // create a new computer
            case 4:
                // prompt for a string (name)
                while ((name = this.promptForString("name : ")).equals("")) {
                    System.out.println("invalid name");
                }

                // prompt for a date for the column introduced
                while ((introduced = this.promptForDate("introduced date (format yyyy-MM-dd) : ")) == null) {
                    System.out.println("invalid date");
                }

                // prompt for a date for the column discontinued
                while ((discontinued = this.promptForDate("discontinued date (format yyyy-MM-dd) : ")) == null) {
                    System.out.println("invalid date");
                }

                // prompt for a long for the id of the company
                while ((companyId = this.promptForLong("company id : ")) <= -1) {
                    System.out.println("invalid id");
                }

                computer = new Computer.ComputerBuilder()
                        .name(name)
                        .introduced(introduced)
                        .discontinued(discontinued)
                        .company(new Company(companyId, ""))
                        .build();

                this.computerRestService.createComputer(computer);

                break;

            // update an existing computer
            case 5:

                // prompt for the computer id
                while ((computerId = this.promptForLong("id : ")) <= 0) {
                    System.out.println("invalid id");
                }

                String tmpPromptName = "new name: ";
                String tmpPromptIntro = "new introduced date: ";
                String tmpPromptDisco = "new introduced date: ";
                String tmpPromptCompanyId = "new company id : ";

                while ((name = this.promptForString(tmpPromptName)).isEmpty()) {
                    System.out.println("invalid name");
                }

                while ((introduced = this.promptForDate(tmpPromptIntro)) == null) {
                    System.out.println("invalid date");
                }

                while ((discontinued = this.promptForDate(tmpPromptDisco)) == null) {
                    System.out.println("invalid date");
                }

                while ((companyId = this.promptForLong(tmpPromptCompanyId)) <= -1) {
                    System.out.println("invalid id");
                }

                computer = new Computer.ComputerBuilder()
                        .id(computerId)
                        .name(name)
                        .introduced(introduced)
                        .discontinued(discontinued)
                        .company(new Company(companyId, ""))
                        .build();

                this.computerRestService.updateComputer(computer);

                break;

            // delete a computer
            case 6:

                while ((computerId = this.promptForLong("id : ")) <= 0) {
                    System.out.println("invalid id");
                }

                this.computerRestService.deleteComputer(computerId);

                break;

            // delete a company
            case 7:
                while ((companyId = this.promptForLong("id : ")) <= 0) {
                    System.out.println("invalid id");
                }

                this.companyRestService.deleteCompany(companyId);

                break;
            // quit
            default:
                stop = true;
                System.out.println("quitting");
        }

        return !stop;
    }

    /**
     * use the scanner to prompt for a Long.
     *
     * @param s String that will be use as an indication for the prompt
     * @return Long returned by the scanner
     */
    private Long promptForLong(String s) {
        Long result;

        System.out.print(s);

        try {
            result = this.sc.nextLong();
        } catch (InputMismatchException e) {
            this.sc.next();
            result = -1L;
        }

        return result;
    }

    /**
     * use the scanner to prompt for a String.
     *
     * @param s String that will be use as an indication for the prompt
     * @return string returned by the scanner
     */
    private String promptForString(String s) {
        String result;

        System.out.print(s);

        try {
            result = this.sc.next();
        } catch (InputMismatchException e) {
            this.sc.next();
            result = "";
        }

        return result;
    }

    /**
     * use the scanner to prompt for a Date.
     *
     * @param s String that will be use as an indication for the prompt
     * @return null if the date is not valid, LocalDate.MIN if empty date
     */
    private LocalDate promptForDate(String s) {

        System.out.print(s);

        String dateString = this.sc.next();

        LocalDate date;

        if ("".equals(dateString)) {
            date = LocalDate.MIN;
        } else {
            try {
                date = LocalDate.parse(dateString, Menu.formatter);
            } catch (DateTimeParseException e) {
                date = null;
            }
        }

        return date;
    }

    /**
     * display the list of computers.
     *
     * @param page page parameters
     * @return false if offset reached the end of the data
     */
    private boolean listComputers(PageParameters page) {
        List<Computer> computers = computerRestService.getList(page);

        computers.forEach(System.out::println);

        return (computers.size() == page.getSize());
    }

    /**
     * display the list of companies.
     *
     * @param page page parameters
     * @return false if offset reached the end of the data
     */
    private boolean listCompanies(PageParameters page) {
        List<Company> companies = this.companyRestService.getList(page);

        companies.forEach(System.out::println);

        return (companies.size() == page.getSize());
    }

    /**
     * show details of a computer based on its id.
     *
     * @param id id of the computer to show
     */
    private void showComputerDetails(final Long id) {

        Computer computer = this.computerRestService.getComputerById(id);

        if (computer != null) {
            System.out.println("Details on computer " + id + " :");
            System.out.println(computer);
        } else {
            System.out.println("Computer of id " + id + " doesn't exist");
        }

    }
}
