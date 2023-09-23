package OOPJAVA;

import java.util.Scanner;

public class Labyrinth {
	static public void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		//int number = scan.nextInt();
		String str = scan.next();
		scan.close();
		int num = Integer.valueOf(str);
		String h = new String("-h");
		if (str == h) {
			System.out.println("Hi");
		}
		System.out.println(num + 1);
	}
}
