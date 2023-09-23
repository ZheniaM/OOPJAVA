package OOPJAVA;

import java.util.Scanner;

public class Labyrinth {
	static public void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Hello World");
		System.out.println("Choose a path");
		String input = scan.next();
		scan.close();
		switch(input){
			case("f"):
			case("front"):
				System.out.println("You went forward");
				break;
			case("r"):
			case("right"):
				System.out.println("You went right");
				break;
			case("l"):
			case("left"):
				System.out.println("You went left");
				break;
			case("--help"):
			case("-h"):
				System.out.println("Write 'l' if you want to go left, 'r' if you want to go right or 'f' if you want to go straight.");
				break;
			default:
				System.out.println("There is no such direction");
				break;
		}
	}
}
