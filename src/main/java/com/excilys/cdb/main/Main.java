package com.excilys.cdb.main;

import java.util.Scanner;

public class Main {

    /**
     * entry method for the CLI app.
     *
     * @param args
     *            arguments
     */
    public static void main(final String[] args) {

        final Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\\n");

        final Menu menu = new Menu(sc);

        while (menu.pick()) {
            System.out.println("pick menu :");
        }

        sc.close();

    }

}
