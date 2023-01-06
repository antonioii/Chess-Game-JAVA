package chessGame.entities.pieces;

import boardGame.entities.Board;
import boardGame.entities.Position;
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
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position aux = new Position(0,0);
		//Marking as true positions above a given piece position:
		aux.setValues(position.getRow() - 1, position.getColumn());
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setRow(aux.getRow() - 1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
		}
		
		//Marking the positions left:
		aux.setValues(position.getRow(), position.getColumn()-1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setColumn(aux.getColumn() - 1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
		}
		
		//Marking the positions right:
		aux.setValues(position.getRow(), position.getColumn() + 1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setColumn(aux.getColumn() + 1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
		}
		
		//Marking as true positions below:
		aux.setValues(position.getRow() + 1, position.getColumn());
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setRow(aux.getRow() + 1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
		}
		
		return matrix;
	}
}