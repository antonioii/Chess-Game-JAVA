package chessGame.entities;
import boardGame.entities.BoardException;

public class ChessException extends BoardException {
	private static final long serialVersionUID = 1L;
	public ChessException(String errorMessage) {
		super(errorMessage);
	}
	
	
}
