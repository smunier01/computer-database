package com.excilys.cdb.main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		MainApp app = new MainApp();
		
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\\n");
		
		Menu menu = new Menu(sc, app);
		
		while (menu.pick()) {}
		
		sc.close();

	}

}
