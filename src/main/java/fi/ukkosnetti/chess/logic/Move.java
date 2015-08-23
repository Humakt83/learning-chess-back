package fi.ukkosnetti.chess.logic;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.logic.object.Piece;

public class Move {

	public final Position original;

	public final Position position;

	public final Piece piece;

	public final Board originalBoard;

	public Move(Position original, Position position, Piece piece, Board originalBoard) {
		this.original = original;
		this.position = position;
		this.piece = piece;
		this.originalBoard = originalBoard;
	}

}
