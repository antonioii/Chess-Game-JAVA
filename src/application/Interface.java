package application;

import chessGame.entities.ChessPiece;

public class Interface {
		public static void printBoard(ChessPiece[][] ChessPieces) {
			System.out.println("    _______________ ");
			for(int i = 0; i<ChessPieces.length; i++) {
				System.out.print((8-i)+"| |");
				for(int j = 0; j< ChessPieces.length; j++) {
				printPiece(ChessPieces[i][j]);	
				}
				System.out.println("");
			}
			System.out.println("    _______________ ");
			System.out.println("    a b c d e f g h ");
		}

		private static void printPiece(ChessPiece chessPiece) {
			if (chessPiece == null) {
				System.out.print("_");
			} else {
				System.out.print(chessPiece);
			}
			System.out.print("|");
		}
		
}
