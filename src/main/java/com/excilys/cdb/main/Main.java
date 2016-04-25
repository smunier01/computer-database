package com.excilys.cdb.main;

import java.util.Scanner;

public class Main {

    public static void main(final String[] args) {

        final Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\\n");

        final Menu menu = new Menu(sc);

        while (menu.pick()) {
        }

        sc.close();

    }

}
