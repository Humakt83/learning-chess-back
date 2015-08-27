package fi.ukkosnetti.chess.dto;

import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fi.ukkosnetti.chess.logic.object.Piece;

public class Move {

	public final Position original;

	public final Position position;

	@JsonIgnore
	public final Piece piece;

	@JsonIgnore
	public final Board originalBoard;
	
	public final boolean pawnDoubleForward;
	
	@JsonIgnore
	public final Consumer<Board> consumer;
	
	public final CastlingState newCastlingState;

	public Move(Position original, Position position, Piece piece, Board originalBoard) {
		this(original, position, piece, originalBoard, false, null, originalBoard.castlingState);
	}
	
	Move(Position original, Position position, Piece piece, Board originalBoard, boolean pawnDoubleForward, Consumer<Board> consumer, CastlingState newCastlingState) {
		this.original = original;
		this.position = position;
		this.piece = piece;
		this.originalBoard = originalBoard;
		this.pawnDoubleForward = pawnDoubleForward;
		this.consumer = consumer;
		this.newCastlingState = newCastlingState;
	}
	
	@SuppressWarnings("unused")
	private Move() {
		this(null, null, null, null, false, null, null);
	}
	
}
