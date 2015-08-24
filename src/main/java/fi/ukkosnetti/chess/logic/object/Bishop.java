package fi.ukkosnetti.chess.logic.object;

import java.util.List;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.logic.MoveUtil;
import fi.ukkosnetti.chess.logic.Position;

public class Bishop extends Piece {

	public Bishop(boolean whitePiece, Position position) {
		super(whitePiece, position, 3);
	}

	@Override
	public List<Board> getMoves(final Board board) {
		return MoveUtil.filterAndTransformMoves(MoveUtil.getDiagonalMoves(board, this));
	}

}
