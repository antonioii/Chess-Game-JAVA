//ALG - CHESS GAME APP
// 30/12/2022
package application;
import java.util.InputMismatchException;
import java.util.Scanner;

import chessGame.entities.ChessException;
import chessGame.entities.ChessMatch;
import chessGame.entities.ChessPiece;
import chessGame.entities.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		
		while (true) {
			//CTRL+C inside the console to stop the app
			try {
				Interface.consoleClear();
				Interface.printBoard(chessMatch.getPieces());
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = Interface.readChessPosition(sc);
				
				System.out.println();
				System.out.print("Destine: ");
				ChessPosition destine = Interface.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, destine);
			}
			catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getLocalizedMessage());
				sc.nextLine();
			}
			
		}
		
		
	}

}