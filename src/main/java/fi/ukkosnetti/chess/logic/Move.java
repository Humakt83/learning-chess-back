package fi.ukkosnetti.chess.logic;

import java.util.function.Consumer;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.logic.object.Piece;

public class Move {

	public final Position original;

	public final Position position;

	public final Piece piece;

	public final Board originalBoard;
	
	public final boolean pawnDoubleForward;
	
	public final Consumer<Board> consumer;

	public Move(Position original, Position position, Piece piece, Board originalBoard) {
		this(original, position, piece, originalBoard, false, null);
	}
	
	Move(Position original, Position position, Piece piece, Board originalBoard, boolean pawnDoubleForward, Consumer<Board> consumer) {
		this.original = original;
		this.position = position;
		this.piece = piece;
		this.originalBoard = originalBoard;
		this.pawnDoubleForward = pawnDoubleForward;
		this.consumer = consumer;
	}
	
}
