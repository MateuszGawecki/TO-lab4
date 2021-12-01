package com.company;

import com.company.gui.MainWindow;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int width, height, numberOfHumans;
        boolean option;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj dlugosc pokoju: ");
        width=scanner.nextInt();
        System.out.println("Podaj szerokosc pokoju: ");
        height=scanner.nextInt();
        System.out.println("Podaj liczbe osobnikow: ");
        numberOfHumans=scanner.nextInt();
        System.out.println("Polulacja nie odporna {true} / Populacja czesciowo odporna {false} ");
        option=scanner.nextBoolean();

        new MainWindow(width, height, numberOfHumans, option);
    }
}
