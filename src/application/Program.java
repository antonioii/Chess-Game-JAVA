//ALG - CHESS GAME APP
// 30/12/2022
package application;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chessGame.entities.ChessException;
import chessGame.entities.ChessMatch;
import chessGame.entities.ChessPiece;
import chessGame.entities.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<ChessPiece>();
		
		while (!chessMatch.getCheckmate()) {
			//CTRL+C inside the console to stop the APP
			try {
				Interface.screenUpdate(); //clean the console log
				Interface.printMatch(chessMatch, captured);
				//Interface.printBoard(chessMatch.getPieces());
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = Interface.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				Interface.screenUpdate();
				Interface.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Destine: ");
				ChessPosition destine = Interface.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, destine);
				if(capturedPiece != null) {
					captured.add(capturedPiece);					
				}
				if(chessMatch.getPromoted() != null) {
					System.out.print("Enter a desired promoted piece (B/H/R/Q): ");
					String type = sc.nextLine().toUpperCase();
					while(!type.equals("B") && !type.equals("H") && !type.equals("R") && !type.equals("Q")) {
						System.out.print("Error: Invalid value! Enter a desired promoted piece (B/H/R/Q): ");
						type = sc.nextLine().toUpperCase();
					}					
					chessMatch.replacePromotedPiece(type);
				}
				
			}
			catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
		}
		Interface.screenUpdate();
		Interface.printMatch(chessMatch, captured);
		
	}

}