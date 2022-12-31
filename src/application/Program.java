//ALG - CHESS GAME APP
// 30/12/2022
package application;
import java.util.Random;

import boardGame.entities.Board;
import boardGame.entities.Position;
import chessGame.entities.ChessMatch;

public class Program {

	public static void main(String[] args) {
		
		//random number between 1 - 8:
		int rand = new Random().nextInt(8)+1;
		//Testing object Position:
		System.out.println("Testing position obj: ");
		Position newPosition = new Position(rand, rand);
		System.out.println(newPosition);
		System.out.println("");
		
		//Testing board creation:
		Board board = new Board(rand, rand);
		System.out.println("Testing board creation: ");
		System.out.println(board.getRows()+", "+board.getColumns()+"\n");
		
		//Testing chess matches with random kings and castles:
		System.out.println("Testing placePiece and printBoard with possible random exceptions:");
		ChessMatch chessMatch = new ChessMatch();
		Interface.printBoard(chessMatch.getPieces());
	}

}