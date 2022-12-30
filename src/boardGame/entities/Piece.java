package boardGame.entities;

public class Piece {
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null; //is not necessary to be declared
	}

	protected Board getBoard() {
		return board;
	}
	
	
	
	
}
