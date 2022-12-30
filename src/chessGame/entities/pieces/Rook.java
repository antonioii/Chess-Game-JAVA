package chessGame.entities.pieces;

import boardGame.entities.Board;
import chessGame.entities.ChessPiece;
import chessGame.entities.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}
	@Override
	public String toString() {
		return "R";
	}
}
