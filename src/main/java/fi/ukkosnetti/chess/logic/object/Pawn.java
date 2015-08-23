package fi.ukkosnetti.chess.logic.object;

import java.util.List;

import fi.ukkosnetti.chess.dto.Board;

public class Pawn extends Piece {

	public Pawn(boolean whitePiece) {
		super(whitePiece, null, 2);
	}

	@Override
	public List<Board> getMoves(final Board board) {
		// TODO Auto-generated method stub
		return null;
	}

}
