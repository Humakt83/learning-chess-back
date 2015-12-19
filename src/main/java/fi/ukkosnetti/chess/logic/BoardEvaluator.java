package fi.ukkosnetti.chess.logic;

import java.util.stream.Stream;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.logic.object.Piece;

public final class BoardEvaluator {
	
	private BoardEvaluator() {}
	
	public static Board evaluateBoard(Board board) {
		board.setEvaluatedValue(Stream.concat(MoveUtil.getPieces(board, true).stream(), MoveUtil.getPieces(board, false)
				.stream())
				.mapToLong(Piece::getEvaluationValue)
				.sum());		
		return board;
	}

}
