package chessGame.entities;

import boardGame.entities.Board;
import boardGame.entities.Piece;
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
	
	//Place new piece using chessPosition and not matrix position:
	private void placePieceInChessPosition(char column, int row, ChessPiece chessPiece) {
		board.placePiece(chessPiece, new ChessPosition(column, row).toPosition());
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition destinePosition) {
		Position source = sourcePosition.toPosition();
		Position destine = destinePosition.toPosition();
		validateSourcePosition(source);
		Piece capturedPiece = makeMove(source, destine);
		return (ChessPiece) capturedPiece;		
	}
	private Piece makeMove(Position source, Position destine) {
		Piece winningPiece = board.removePiece(source);
		Piece capturedPiece = board.removePiece(destine);
		board.placePiece(winningPiece, destine);
		return capturedPiece;
	}
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("ERROR: There is no piece on the source position.");
		}
	}
	
	private void initialSetup() {
		/*TESTS SNIPPET:
		//Testing snippet with random rows and columns:
		board.placePiece(new King(board, Color.WHITE), new Position(new Random().nextInt(8),new Random().nextInt(8)));
		board.placePiece(new King(board, Color.BLACK), new Position(new Random().nextInt(8),new Random().nextInt(8)));
		board.placePiece(new Rook(board, Color.WHITE), new Position(new Random().nextInt(8),new Random().nextInt(8)));
		
		//Testing snippet with a given chess position:
		placePieceInChessPosition('h',8, new Rook(board, Color.BLACK));
		*/
		
		placePieceInChessPosition('c', 1, new Rook(board, Color.WHITE));
		placePieceInChessPosition('c', 2, new Rook(board, Color.WHITE));
		placePieceInChessPosition('d', 2, new Rook(board, Color.WHITE));
		placePieceInChessPosition('e', 2, new Rook(board, Color.WHITE));
		placePieceInChessPosition('e', 1, new Rook(board, Color.WHITE));
		placePieceInChessPosition('d', 1, new King(board, Color.WHITE));

		placePieceInChessPosition('c', 7, new Rook(board, Color.BLACK));
		placePieceInChessPosition('c', 8, new Rook(board, Color.BLACK));
		placePieceInChessPosition('d', 7, new Rook(board, Color.BLACK));
		placePieceInChessPosition('e', 7, new Rook(board, Color.BLACK));
		placePieceInChessPosition('e', 8, new Rook(board, Color.BLACK));
		placePieceInChessPosition('d', 8, new King(board, Color.BLACK));
		
	}
	
}
