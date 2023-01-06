package boardGame.entities;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if(rows <1 || columns <1) {
			throw new BoardException("ERROR: To create a board, you need at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	public int getRows() {
		return rows;
	}
	public int getColumns() {
		return columns;
	}	
	
	//Access piece through board position:
	public Piece piece(int row, int column) {
		if(!positionExists(row, column)) {
			throw new BoardException("ERROR: Tried to access a position with invalid rows/columns.");
		}
		return pieces[row][column];
	}
	//Access piece through piece position:
	public Piece piece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("ERROR: Tried to access a position that doesn't exist on the board!");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on that position!");
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	public Piece removePiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("ERROR: Tried to remove from an invalid position!");
		}
		if(piece(position) == null) {
			return null;
		} 
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("ERROR: You can't place a piece in a invalid position.");
		}
		return piece(position) != null;
	}
}
