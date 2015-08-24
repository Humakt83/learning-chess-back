package fi.ukkosnetti.chess.logic.object;

import java.util.List;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.logic.Position;

public class Pawn extends Piece {

	public Pawn(boolean whitePiece, Position position) {
		super(whitePiece, position, 2);
	}

	@Override
	public List<Board> getMoves(final Board board) {
		// TODO Auto-generated method stub
		return null;
	}

}
