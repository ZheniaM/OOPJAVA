package OOPJAVA;

import java.util.Scanner;

public class Labyrinth {
	static public void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		int number = scan.nextInt();
		scan.close();
		if(number.equals("-help")||number.equals("-h")){
			System.out.println("We tried");
		}
		System.out.println(per + 1);
		Scanner scan = new Scanner(System.in);
		System.out.println("Hello World");
		System.out.println("Choose a path");
		String input = scan.next();
		switch(input){
		    case("f"):
		        System.out.println("You went forward");
		        break;
		    case("r"):
		        System.out.println("You went right");
		        break;
		    case("l"):
		        System.out.println("You went left");
		        break;
            case("/help"):
                System.out.println("Write 'l' if you want to go left, 'r' if you want to go right or 'f' if you want to go straight.");
		        break;
		    default:
		        System.out.println("There is no such direction");
		        break;
		}
	}
}
