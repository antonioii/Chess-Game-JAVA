package chessGame.entities.pieces;
import boardGame.entities.Board;
import boardGame.entities.Position;
import chessGame.entities.ChessMatch;
import chessGame.entities.ChessPiece;
import chessGame.entities.Color;

public class Pawn extends ChessPiece {
	private ChessMatch chessMatch;
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0,0);
		if(getColor() ==Color.WHITE) {
			
			//in order to move one position forward:
			p.setValues(position.getRow()-1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//in order to move two positions forward at once, pawns can go two positions in the first move:
				// .p. -> destine position
				// .p2. -> intermediate 
				// .piece. -> source position
			p.setValues(position.getRow()-2, position.getColumn());
			Position p2 = new Position(position.getRow()-1, position.getColumn()); //middle position
			if(getBoard().positionExists(p) && 
					!getBoard().thereIsAPiece(p) && 
					getMoveCount() == 0 &&
					getBoard().positionExists(p2) &&
					!getBoard().thereIsAPiece(p2)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//move to a diagonal capturing an opponent piece:
			p.setValues(position.getRow()-1, position.getColumn()-1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow()-1, position.getColumn()+1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//special move: en passant
			if( position.getRow() == 3 ) {
				Position left = new Position(position.getRow(), position.getColumn()-1);
				if( getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable() ) {
					mat[left.getRow()-1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn()+1);
				if( getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable() ) {
					mat[right.getRow()-1][right.getColumn()] = true;
				}				
			}
			
			
		} else { //if getColor() == BLACK

			//in order to move one position forward:
			p.setValues(position.getRow()+1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//in order to move two positions forward at once, pawns can go two positions in the first move:
				// .p. -> destine position
				// .p2. -> intermediate 
				// .piece. -> source position
			p.setValues(position.getRow()+2, position.getColumn());
			Position p2 = new Position(position.getRow()+1, position.getColumn()); //middle position
			if(getBoard().positionExists(p) && 
					!getBoard().thereIsAPiece(p) && 
					getMoveCount() == 0 &&
					getBoard().positionExists(p2) &&
					!getBoard().thereIsAPiece(p2)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//move to a diagonal capturing an opponent piece:
			p.setValues(position.getRow()+1, position.getColumn()-1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow()+1, position.getColumn()+1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//special move: en passant
			if( position.getRow() == 4 ) {
				Position left = new Position(position.getRow(), position.getColumn()-1);
				if( getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable() ) {
					mat[left.getRow()+1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn()+1);
				if( getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable() ) {
					mat[right.getRow()+1][right.getColumn()] = true;
				}				
			}
			
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}
	
}
