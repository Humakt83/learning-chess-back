package fi.ukkosnetti.chess.logic.object;

import java.util.List;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Move;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.logic.MoveUtil;

public class Queen extends Piece {

	public Queen(boolean whitePiece, Position position) {
		super(whitePiece, position, 5);
	}

	@Override
	public List<Board> getMoves(final Board board) {
		List<Move> moves = MoveUtil.getHorizontalAndVerticalMoves(board, this);
		moves.addAll(MoveUtil.getDiagonalMoves(board, this));
		return MoveUtil.filterAndTransformMoves(moves);
	}

}
