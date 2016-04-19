package com.excilys.cdb.main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\\n");
		
		Menu menu = new Menu(sc);
		
		while (menu.pick()) {}
		
		sc.close();

	}

}
