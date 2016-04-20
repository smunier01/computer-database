package com.excilys.cdb.main;

import java.util.Scanner;

import com.excilys.sdb.service.ComputerService;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\\n");

		ComputerService service = new ComputerService();

		Menu menu = new Menu(sc);

		while (menu.pick()) {
		}

		sc.close();

	}

}
