package OOPJAVA;

import java.io.IOException;
import java.util.Scanner;

public class Labyrinth {
	static public void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		int number;
		System.out.print("Enter number: ");
		while (true) {
			try {
				number = scan.nextInt();
			} catch (IOException error) {
				System.out.println("try again");
				continue;
			}
			break;
		}
		scan.close();
		System.out.println(number + 1);
	}
}
