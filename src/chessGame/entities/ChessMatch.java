package chessGame.entities;

import java.util.ArrayList;
import java.util.List;

import boardGame.entities.Board;
import boardGame.entities.Piece;
import boardGame.entities.Position;
import chessGame.entities.pieces.King;
import chessGame.entities.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private List<Piece> piecesOnTheBoard = new ArrayList<Piece>();
	private List<Piece> capturedPieces = new ArrayList<Piece>();
		
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	public Color getCurrentPlayer() {
		return currentPlayer;
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
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition destinePosition) {
		Position source = sourcePosition.toPosition();
		Position destine = destinePosition.toPosition();
		validateSourcePosition(source);
		validateDestinePosition(source, destine);
		Piece capturedPiece = makeMove(source, destine);
		nextTurn();
		return (ChessPiece)capturedPiece;		
	}
	
	private Piece makeMove(Position source, Position destine) {
		Piece winningPiece = board.removePiece(source);
		Piece capturedPiece = board.removePiece(destine);
		board.placePiece(winningPiece, destine);
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("ERROR: There is no piece on the source position.");
		}
		if(currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("ERROR: Chosen piece is not yours.");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("ERROR: There is no possible moves for the choosen piece!");
		}
	}
	
	private void validateDestinePosition(Position source, Position destine) {
		if(!board.piece(source).possibleMove(destine)) {
			throw new ChessException("The chosen piece can't move to destine position.");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ?
				Color.BLACK : 
				Color.WHITE;
	}
	
	//Place new piece using chessPosition and not matrix position:
	private void placePieceInChessPosition(char column, int row, ChessPiece chessPiece) {
		board.placePiece(chessPiece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(chessPiece);
	}
	
	private void initialSetup() {
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
