package fi.ukkosnetti.chess.dto;

import java.util.Arrays;

import fi.ukkosnetti.chess.logic.Move;
import fi.ukkosnetti.chess.logic.Position;

public class Board {

	public final Integer[][] board;

	public final Boolean turnOfWhite;
	
	public final Move lastMove;

	public Board(Integer[][] board, Boolean turnOfWhite, Move lastMove) {
		this.board = board;
		this.turnOfWhite = turnOfWhite;
		this.lastMove = lastMove;
	}
	
	public Board(Integer[][] board, Boolean turnOfWhite) {
		this(board, turnOfWhite, null);
	}

	@SuppressWarnings("unused")
	private Board() {
		this(null, null, null);
	}

	@Override
	public String toString() {
		return "Board [board=" + Arrays.toString(board) + ", turnOfWhite=" + turnOfWhite + "]";
	}
	
	public int getSlot(Position pos) {
		return board[pos.x][pos.y];
	}

}
