package fi.ukkosnetti.chess.logic.object;

import fi.ukkosnetti.chess.dto.Position;

public abstract class Piece implements Movable {

	public final boolean whitePiece;

	private final int pieceValue;
	
	public final Position position;

	Piece(boolean whitePiece, Position position, int piece) {
		this.whitePiece = whitePiece;
		this.pieceValue = piece;
		this.position = position;
	}

	public int getSign() {
		return whitePiece ? 1 : -1;
	}
	
	public int getPieceValue() {
		return pieceValue * getSign();
	}

}
