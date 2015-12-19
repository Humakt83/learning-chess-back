package fi.ukkosnetti.chess.logic.object;

import java.util.stream.Stream;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Position;

public abstract class Piece implements Movable {

	public final boolean whitePiece;

	private final int pieceValue;
	
	private final int evaluationValue;
	
	public final Position position;

	Piece(boolean whitePiece, Position position, int piece, int evaluationValue) {
		this.whitePiece = whitePiece;
		this.pieceValue = piece;
		this.position = position;
		this.evaluationValue = evaluationValue;
	}

	public int getSign() {
		return whitePiece ? 1 : -1;
	}
	
	public int getPieceValue() {
		return pieceValue * getSign();
	}
	
	public int getEvaluationValue() {
		return evaluationValue * getSign();
	}
	
	@Override
	public boolean canEatKing(final Board board) {
		final int kingToFind = whitePiece ? -6 : 6;
		return !getMoves(board)
			.stream()
			.filter(futureBoard -> Stream.of(futureBoard.board).flatMap(Stream::of).filter(i -> i == kingToFind).findAny().orElse(null) == null)
			.findAny()
			.isPresent();
	}

}