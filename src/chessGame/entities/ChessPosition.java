package chessGame.entities;

import boardGame.entities.Position;

public class ChessPosition {
	
	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		if(column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error: Wrong chess position, valid positions are from a1 - h8");
		}
		this.column = column;
		this.row = row;
	}
	
	public char getColumn() {
		return column;
	}
	
	public int getRow() {
		return row;
	}
	
	//Conversions:
	//Chess Row(CM) = 8 - Matrix Row(MR) .: MR = 8 - CR
	//Chess column(CC) = Matrix column(MC) - UNICODE<a> .: MC = 'a'-CC		
	protected Position toPosition() {
		return new Position(8-row, column - 'a');
	}	
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a'+position.getColumn()), 8-position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row;
	}

}
