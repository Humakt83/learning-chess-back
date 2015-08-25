package fi.ukkosnetti.chess.logic;

import java.util.function.Consumer;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.logic.object.Piece;

public class MoveBuilder {
	
	private boolean pawnDoubleForward = false;
	
	private final Position originalPosition;
	
	private final Position position;
	
	private final Piece piece;
	
	private final Board originalBoard;
	
	private Consumer<Board> consumer = null;

	public MoveBuilder(Position originalPosition, Position position, Piece piece, Board originalBoard) {
		this.originalPosition = originalPosition;
		this.position = position;
		this.piece = piece;
		this.originalBoard = originalBoard;
	}
	
	public MoveBuilder setPawnDoubleForward(boolean pawnDoubleForward) {
		this.pawnDoubleForward = pawnDoubleForward;
		return this;
	}
	
	public MoveBuilder setConsumer(Consumer<Board> consumer) {
		this.consumer = consumer;
		return this;
	}
	
	public Move build() {
		return new Move(originalPosition, position, piece, originalBoard, pawnDoubleForward, consumer);
	}
}
