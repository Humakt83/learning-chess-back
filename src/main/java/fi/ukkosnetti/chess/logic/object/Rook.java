package fi.ukkosnetti.chess.logic.object;

import java.util.List;
import java.util.stream.Collectors;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Move;
import fi.ukkosnetti.chess.dto.MoveBuilder;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.dto.CastlingState.CastlingBlocker;
import fi.ukkosnetti.chess.logic.MoveUtil;

public class Rook extends Piece {

	public Rook(boolean whitePiece, Position position) {
		super(whitePiece, position, 4, 125);
	}

	@Override
	public List<Board> getMoves(final Board board) {
		List<Move> moves = MoveUtil.getHorizontalAndVerticalMoves(board, this)
				.stream()
				.map(move -> new MoveBuilder(move.original, move.position, move.piece, move.originalBoard).addCastlingBlocker(getCastlingBlocker()).build())
				.collect(Collectors.toList());
		return MoveUtil.filterAndTransformMoves(moves);
	}
	
	private CastlingBlocker getCastlingBlocker() {
		CastlingBlocker blocker = null;
		if (position.x == 0) {
			blocker = whitePiece ? CastlingBlocker.WHITE_LEFT_ROOK_MOVED : CastlingBlocker.BLACK_LEFT_ROOK_MOVED;
		} else if (position.x == 7) {
			blocker = whitePiece ? CastlingBlocker.WHITE_RIGHT_ROOK_MOVED : CastlingBlocker.BLACK_RIGHT_ROOK_MOVED;
		}
		return blocker;
	}

}
