package chessGame.entities;

import java.util.Random;

import boardGame.entities.Board;
import boardGame.entities.Position;
import chessGame.entities.pieces.King;
import chessGame.entities.pieces.Rook;

public class ChessMatch {
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] pieceMatrix = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i < board.getRows(); i++) {
			for (int j=0; j < board.getColumns(); j++) {
				pieceMatrix[i][j] =  (ChessPiece) board.piece(i, j);
			}
		}
		return pieceMatrix;
	}
	
	private void initialSetup() {
		//testing snippets with random rows and columns:		
		board.placePiece(new King(board, Color.WHITE), new Position(new Random().nextInt(8),new Random().nextInt(8)));
		board.placePiece(new King(board, Color.BLACK), new Position(new Random().nextInt(8),new Random().nextInt(8)));
		board.placePiece(new Rook(board, Color.WHITE), new Position(new Random().nextInt(8),new Random().nextInt(8)));
		board.placePiece(new Rook(board, Color.WHITE), new Position(new Random().nextInt(8),new Random().nextInt(8)));
	}
}
