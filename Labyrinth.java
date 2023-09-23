package OOPJAVA;

import java.io.Console;
import java.util.Scanner;

public class Labyrinth {
	static public void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		var number = scan.next();
		try {
			int per = Integer.valueOf(number);
		}
		scan.close();
		if(number.equals("-help")||number.equals("-h")){
			System.out.println("We tried");
		}
		System.out.println(per + 1);
	}
}
