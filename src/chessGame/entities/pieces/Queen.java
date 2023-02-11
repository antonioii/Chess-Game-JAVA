package chessGame.entities.pieces;

import boardGame.entities.Board;
import boardGame.entities.Position;
import chessGame.entities.ChessPiece;
import chessGame.entities.Color;

public class Queen extends ChessPiece {

	public Queen(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "Q";
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
		
		//Marking as true positions in up-left diagonal from a given piece position:
		aux.setValues(position.getRow() - 1, position.getColumn()-1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setValues(aux.getRow()-1, aux.getColumn()-1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
		}
		
		//Marking the positions up-right diagonal:
		aux.setValues(position.getRow()-1, position.getColumn()+1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setValues(aux.getRow()-1, aux.getColumn()+1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
		}
		
		//Marking the positions down-right diagonal:
		aux.setValues(position.getRow()+1, position.getColumn() + 1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setValues(aux.getRow()+1, aux.getColumn()+1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
		}
		
		//Marking as true positions down-left diagonal:
		aux.setValues(position.getRow() + 1, position.getColumn()-1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setValues(aux.getRow()+1,aux.getColumn()-1);
		}
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
		}
				
		return matrix;
	}
}