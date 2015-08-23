package fi.ukkosnetti.chess.dto;

import java.util.Arrays;

public class Board {

	public final Integer[][] board;

	public final Boolean turnOfWhite;

	public Board(Integer[][] board, Boolean turnOfWhite) {
		this.board = board;
		this.turnOfWhite = turnOfWhite;
	}

	@SuppressWarnings("unused")
	private Board() {
		this(null, null);
	}

	@Override
	public String toString() {
		return "Board [board=" + Arrays.toString(board) + ", turnOfWhite=" + turnOfWhite + "]";
	}

}
