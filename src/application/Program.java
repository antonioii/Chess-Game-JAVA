//ALG - CHESS GAME APP
// 30/12/2022
package application;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import boardGame.entities.Board;
import boardGame.entities.Position;
import chessGame.entities.ChessMatch;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		//random number between 1 - 8:
		int rand = new Random().nextInt(8)+1;
		//Testing object Position:
		System.out.println("Testing position obj: ");
		Position newPosition = new Position(rand, rand);
		System.out.println(newPosition);
		System.out.println("");
		
		//Testing board creation:
		Board board = new Board(rand, rand);
		
		//Testing chess match:
		ChessMatch chessMatch = new ChessMatch();
		Interface.printBoard(chessMatch.getPieces());
		
		
		sc.close();
	}

}