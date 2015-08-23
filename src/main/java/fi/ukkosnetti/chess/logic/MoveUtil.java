package fi.ukkosnetti.chess.logic;

import java.util.List;
import java.util.stream.Collectors;

import fi.ukkosnetti.chess.dto.Board;

public final class MoveUtil {

	private final static int MIN_COORD = 0, MAX_COORD = 7;

	private static boolean isMoveOnBoard(Move move) {
		Position pos = move.position;
		return pos.x >= MIN_COORD && pos.x <= MAX_COORD && pos.y >= MIN_COORD && pos.y <= MAX_COORD;
	}
	
	private static boolean filterMovesThatCollideWithOwnPiece(Move move) {
		int sign = move.piece.getSign();
		Integer[][] b = move.originalBoard.board;
		int slot = b[move.position.x][move.position.y];
		return (slot <= 0 && sign > 0) || (slot >= 0 && sign < 0);
	}

	private static Board transformMove(Move move) {
		Integer[][] b = move.originalBoard.board;
		b[move.position.x][move.position.y] = move.piece.getPieceValue();
		b[move.original.x][move.original.y] = 0;
		return new Board(b, !move.originalBoard.turnOfWhite);
	}

	public static List<Board> filterAndTransformMoves(List<Move> moves) {
		return moves.stream().filter(MoveUtil::isMoveOnBoard).filter(MoveUtil::filterMovesThatCollideWithOwnPiece).map(MoveUtil::transformMove).collect(Collectors.toList());
	}
	
}
