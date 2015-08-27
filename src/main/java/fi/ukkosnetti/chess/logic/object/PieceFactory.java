package fi.ukkosnetti.chess.logic.object;

import fi.ukkosnetti.chess.dto.Position;

public class PieceFactory {

	public static Piece createPiece(int pieceValue, Position position) {
		Piece piece = null;
		switch(Math.abs(pieceValue)) {
		case 1:
			piece = new Pawn(pieceValue > 0, position);
			break;
		case 2:
			piece = new Knight(pieceValue > 0, position);
			break;
		case 3:
			piece = new Bishop(pieceValue > 0, position);
			break;
		case 4:
			piece = new Rook(pieceValue > 0, position);
			break;
		case 5:
			piece = new Queen(pieceValue > 0, position);
			break;
		case 6:
			piece = new King(pieceValue > 0, position);
			break;
		default:
			throw new IllegalArgumentException("Unsupported value for piece: " + pieceValue);
		}
		
		return piece;
	}
}
