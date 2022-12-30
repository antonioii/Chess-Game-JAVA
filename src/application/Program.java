//ALG - CHESS GAME APP
// 30/12/2022
package application;
import java.util.Locale;
import java.util.Scanner;

import boardGame.entities.Position;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Testing position obj: ");
		Position newPosition = new Position(sc.nextInt(), sc.nextInt());
		System.out.println(newPosition);
		
		sc.close();
	}

}