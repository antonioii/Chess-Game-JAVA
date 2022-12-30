package application;

import chessGame.entities.ChessPiece;

public class Interface {
	public static void printBoard(ChessPiece[][] pieces) {
		System.out.println("    _______________________________");		
		for (int i=0; i<pieces.length; i++) {
			System.out.println(" | |   |   |   |   |   |   |   |   |");
			System.out.print((8-i)+"| ");
			for (int j=0; j<pieces.length; j++) {
				printPiece(pieces[i][j]);
			}
			System.out.println("|");
		}
		System.out.println("   =================================");
		System.out.println("     a   b   c   d   e   f   g   h  ");
	}
	
	private static void printPiece(ChessPiece piece) {
		System.out.print("|_");
		if (piece == null) {
			System.out.print("-");
		}
		else {
			
			System.out.print(piece);
		}
		System.out.print("_");
	}
}
