package fi.ukkosnetti.chess.logic.object;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Move;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.logic.MoveUtil;

public class Knight extends Piece {

	public Knight(boolean whitePiece, Position position) {
		super(whitePiece, position, 2, 95);
	}

	@Override
	public List<Board> getMoves(final Board board) {
		List<Position> positions = Arrays.asList(position.newPosition(1, 2), position.newPosition(1, -2), position.newPosition(-1, 2), position.newPosition(-1, -2), position.newPosition(2, 1),
				position.newPosition(2, -1), position.newPosition(-2, 1), position.newPosition(-2, -1));
		List<Move> moves = positions.stream().map(p -> {
			return new Move(position, p, this, board);
		}).collect(Collectors.toList());
		return MoveUtil.filterAndTransformMoves(moves);
	}

}
