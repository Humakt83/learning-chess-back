package fi.ukkosnetti.chess.logic.object;


public abstract class Piece implements Movable {

	final boolean whitePiece;

	Piece(boolean whitePiece) {
		this.whitePiece = whitePiece;
	}

}
