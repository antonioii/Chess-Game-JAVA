package chessGame.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardGame.entities.Board;
import boardGame.entities.Piece;
import boardGame.entities.Position;
import chessGame.entities.pieces.Bishop;
import chessGame.entities.pieces.King;
import chessGame.entities.pieces.Knight;
import chessGame.entities.pieces.Pawn;
import chessGame.entities.pieces.Queen;
import chessGame.entities.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private List<Piece> piecesOnTheBoard = new ArrayList<Piece>();
	private List<Piece> capturedPieces = new ArrayList<Piece>();
	private boolean checkmate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		check = false; //redundant, boolean type 'starts as false'
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	public boolean getCheck() {
		return check;
	}
	public boolean getCheckmate() {
		return checkmate;
	}
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	public ChessPiece getPromoted() {
		return promoted;
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
		//chess move:
		Position source = sourcePosition.toPosition();
		Position destine = destinePosition.toPosition();
		validateSourcePosition(source);
		validateDestinePosition(source, destine);
		Piece capturedPiece = makeMove(source, destine);
		
		//special move: en passant
		ChessPiece movedPiece = (ChessPiece)board.piece(destine);
		if(movedPiece instanceof Pawn && ( (destine.getRow()==source.getRow()-2) || (destine.getRow()==source.getRow()+2) ) ){
			enPassantVulnerable = movedPiece;
		} else {
			enPassantVulnerable = null;
		}		
		
		//testing if it is in 'check':
		if(testCheck(currentPlayer)) {
			undoMove(source, destine, capturedPiece);
			throw new ChessException("You can't put yourself in check!");
		}
		
			//special move: promotion
			promoted = null;
			if(movedPiece instanceof Pawn) {
				if( (movedPiece.getColor() == Color.WHITE && destine.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && destine.getRow() == 7) ) {
					promoted = (ChessPiece)board.piece(destine);
					promoted = replacePromotedPiece("Q");
				}
			}
			
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		//if not in checkmate, begin next turn:
		if(testCheckmate(opponent(currentPlayer))) {
			checkmate = true;
		} else {
			nextTurn();
		}
		return (ChessPiece)capturedPiece;		
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if(promoted == null) {
			throw new IllegalStateException("ERROR: There is no piece to be promoted.");
		}
		if(!type.equals("B") && !type.equals("H") && !type.equals("R") && !type.equals("Q")) {
			return promoted;
		}
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		return newPiece;		
	}
	
	private ChessPiece newPiece(String type, Color color) {
		if(type.equals("B")) {return new Bishop(board, color);}
		if(type.equals("H")) {return new Knight(board, color);}
		if(type.equals("R")) {return new Rook(board, color);}
		return new Queen(board, color); //doesn't need a test because other options have already been tested
	}
	
	private Piece makeMove(Position source, Position destine) {
		ChessPiece winningPiece = (ChessPiece)board.removePiece(source);
		winningPiece.increaseMoveCount();
		Piece capturedPiece = board.removePiece(destine);
		board.placePiece(winningPiece, destine);
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		//special move: small castling
		if(winningPiece instanceof King && destine.getColumn() == source.getColumn()+2) {
			Position sourceT = new Position(source.getRow(), source.getColumn()+3);
			Position targetT = new Position(source.getRow(), source.getColumn()+1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		//special move: big castling
		if(winningPiece instanceof King && destine.getColumn() == source.getColumn()-2) {
			Position sourceT = new Position(source.getRow(), source.getColumn()-4);
			Position targetT = new Position(source.getRow(), source.getColumn()-1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		//special move: en passant
		if( winningPiece instanceof Pawn ) {
			if(source.getColumn() != destine.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				if(winningPiece.getColor() == Color.WHITE) {
					pawnPosition = new Position(destine.getRow()+1, destine.getColumn());
				} else {
					pawnPosition = new Position(destine.getRow()-1, destine.getColumn());	
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position destine, Piece capturedPiece) {
		//logic to undo the makeMove method:
		ChessPiece winningPiece = (ChessPiece)board.removePiece(destine);
		winningPiece.decreaseMoveCount();
		board.placePiece(winningPiece, source);
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, destine);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		//special move: small castling
		if(winningPiece instanceof King && destine.getColumn() == source.getColumn()+2) {
			Position sourceT = new Position(source.getRow(), source.getColumn()+3);
			Position targetT = new Position(source.getRow(), source.getColumn()+1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		//special move: big castling
		if(winningPiece instanceof King && destine.getColumn() == source.getColumn()-2) {
			Position sourceT = new Position(source.getRow(), source.getColumn()-4);
			Position targetT = new Position(source.getRow(), source.getColumn()-1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		//special move: en passant
		if( winningPiece instanceof Pawn ) {
			if(source.getColumn() != destine.getColumn() && capturedPiece == enPassantVulnerable) {
				ChessPiece pawn = (ChessPiece)board.removePiece(destine);
				Position pawnPosition;
				if(winningPiece.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, destine.getColumn());
				} else {
					pawnPosition = new Position(4, destine.getColumn());	
				}
				board.placePiece(pawn, pawnPosition);
			}
		}

		
		
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
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ?
				Color.BLACK :
				Color.WHITE;
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if(p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("CRITICAL ERROR: There is no "+color+" king in the game!");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition(); //convert matrix to position
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for(Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckmate(Color color) {
		if(!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list) {
			boolean[][] matrixOfPossibleMoves = p.possibleMoves();
			for(int i=0;i<board.getRows();i++) {
				for(int j=0; j<board.getColumns();j++) {
					if(matrixOfPossibleMoves[i][j]) {
						Position initialPlace = ((ChessPiece)p).getChessPosition().toPosition();
						Position futurePlace = new Position(i,j);
						Piece capturedPiece = makeMove(initialPlace, futurePlace);
						boolean testCheck = testCheck(color);
						undoMove(initialPlace, futurePlace, capturedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	//Place new piece using chessPosition and not matrix position:
	private void placePieceInChessPosition(char column, int row, ChessPiece chessPiece) {
		board.placePiece(chessPiece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(chessPiece);
	}
	
	private void initialSetup() {
		placePieceInChessPosition('a', 1, new Rook(board, Color.WHITE));
		placePieceInChessPosition('b', 1, new Knight(board, Color.WHITE));
		placePieceInChessPosition('c', 1, new Bishop(board, Color.WHITE));
		placePieceInChessPosition('d', 1, new Queen(board, Color.WHITE));
		placePieceInChessPosition('e', 1, new King(board, Color.WHITE, this));
		placePieceInChessPosition('h', 1, new Rook(board, Color.WHITE));
		placePieceInChessPosition('f', 1, new Bishop(board, Color.WHITE));
		placePieceInChessPosition('g', 1, new Knight(board, Color.WHITE));
		placePieceInChessPosition('a', 2, new Pawn(board, Color.WHITE, this));
		placePieceInChessPosition('b', 2, new Pawn(board, Color.WHITE, this));
		placePieceInChessPosition('c', 2, new Pawn(board, Color.WHITE, this));
		placePieceInChessPosition('d', 2, new Pawn(board, Color.WHITE, this));
		placePieceInChessPosition('e', 2, new Pawn(board, Color.WHITE, this));
		placePieceInChessPosition('f', 2, new Pawn(board, Color.WHITE, this));
		placePieceInChessPosition('g', 2, new Pawn(board, Color.WHITE, this));
		placePieceInChessPosition('h', 2, new Pawn(board, Color.WHITE, this));



		placePieceInChessPosition('a', 8, new Rook(board, Color.BLACK));
		placePieceInChessPosition('b', 8, new Knight(board, Color.BLACK));
		placePieceInChessPosition('c', 8, new Bishop(board, Color.BLACK));
		placePieceInChessPosition('d', 8, new Queen(board, Color.BLACK));
		placePieceInChessPosition('e', 8, new King(board, Color.BLACK, this));
		placePieceInChessPosition('f', 8, new Bishop(board, Color.BLACK));
		placePieceInChessPosition('g', 8, new Knight(board, Color.BLACK));
		placePieceInChessPosition('h', 8, new Rook(board, Color.BLACK));
		placePieceInChessPosition('a', 7, new Pawn(board, Color.BLACK, this));
		placePieceInChessPosition('b', 7, new Pawn(board, Color.BLACK, this));
		placePieceInChessPosition('c', 7, new Pawn(board, Color.BLACK, this));
		placePieceInChessPosition('d', 7, new Pawn(board, Color.BLACK, this));
		placePieceInChessPosition('e', 7, new Pawn(board, Color.BLACK, this));
		placePieceInChessPosition('f', 7, new Pawn(board, Color.BLACK, this));
		placePieceInChessPosition('g', 7, new Pawn(board, Color.BLACK, this));
		placePieceInChessPosition('h', 7, new Pawn(board, Color.BLACK, this));

		
	}
	
}
