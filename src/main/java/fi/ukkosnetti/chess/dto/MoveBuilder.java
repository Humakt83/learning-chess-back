package fi.ukkosnetti.chess.dto;

import java.util.function.Consumer;

import fi.ukkosnetti.chess.dto.CastlingState.CastlingBlocker;
import fi.ukkosnetti.chess.logic.object.Piece;

public class MoveBuilder {
	
	private boolean pawnDoubleForward = false;
	
	private final Position originalPosition;
	
	private final Position position;
	
	private final Piece piece;
	
	private final Board originalBoard;
	
	private Consumer<Board> consumer = null;
	
	private final CastlingState castlingState;

	public MoveBuilder(Position originalPosition, Position position, Piece piece, Board originalBoard) {
		this.originalPosition = originalPosition;
		this.position = position;
		this.piece = piece;
		this.originalBoard = originalBoard;
		this.castlingState = originalBoard.castlingState.copy();
	}
	
	public MoveBuilder setPawnDoubleForward(boolean pawnDoubleForward) {
		this.pawnDoubleForward = pawnDoubleForward;
		return this;
	}
	
	public MoveBuilder setConsumer(Consumer<Board> consumer) {
		this.consumer = consumer;
		return this;
	}
	
	public MoveBuilder addCastlingBlocker(CastlingBlocker blocker) {
		if (blocker != null && !this.castlingState.blockers.contains(blocker))	this.castlingState.blockers.add(blocker);
		return this;
	}
	
	public Move build() {
		return new Move(originalPosition, position, piece, originalBoard, pawnDoubleForward, consumer, castlingState);
	}
}
