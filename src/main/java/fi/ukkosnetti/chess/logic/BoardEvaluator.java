package fi.ukkosnetti.chess.logic;

import java.util.stream.Stream;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.rules.MoveUtil;

public final class BoardEvaluator {
	
	private BoardEvaluator() {}
	
	public static Board evaluateBoard(final Board board) {
		board.setEvaluatedValue(Stream.concat(MoveUtil.getPieces(board, true).stream(), MoveUtil.getPieces(board, false)
				.stream())
				.mapToLong(piece -> piece.getEvaluationValue(board))
				.sum());		
		return board;
	}

}
