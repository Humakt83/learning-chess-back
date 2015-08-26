package fi.ukkosnetti.chess.dto;

import java.util.Arrays;

import fi.ukkosnetti.chess.logic.Move;
import fi.ukkosnetti.chess.logic.Position;

public class Board {

	public final Integer[][] board;

	public final Boolean turnOfWhite;
	
	public final Move lastMove;
	
	public final CastlingState castlingState;

	public Board(Integer[][] board, Boolean turnOfWhite, Move lastMove, CastlingState castlingState) {
		this.board = board;
		this.turnOfWhite = turnOfWhite;
		this.lastMove = lastMove;
		this.castlingState = castlingState;
	}
	
	public Board(Integer[][] board, Boolean turnOfWhite) {
		this(board, turnOfWhite, null, new CastlingState());
	}

	@SuppressWarnings("unused")
	private Board() {
		this(null, null, null, null);
	}
	
	public int getSlot(Position pos) {
		return board[pos.x][pos.y];
	}

	@Override
	public String toString() {
		return "Board [board=" + Arrays.toString(board) + ", turnOfWhite=" + turnOfWhite + ", lastMove=" + lastMove + ", castlingState=" + castlingState + "]";
	}
	
	

}
