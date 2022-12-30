package chessGame.entities;

import boardGame.entities.Board;

public class ChessMatch {
	private Board board = new Board(8,8);
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] pieceMatrix = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i =0; i<board.getRows(); i++) {
			for(int j=0; i<board.getColumns(); i++) {
				pieceMatrix[i][j] =  (ChessPiece) board.piece(i, j);
			}
		}
		return pieceMatrix;
	}
	
	
}
