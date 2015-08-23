package fi.ukkosnetti.chess.test.util;

import fi.ukkosnetti.chess.logic.object.Piece;

public class BoardUtil {

	public static Integer[][] createEmptyBoard() {
		return new Integer[][] {
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }
		};
	}

	public static Integer[][] createBoardWithPieces(Piece... pieces) {
		Integer[][] board = createEmptyBoard();
		for (Piece piece : pieces) {
			board[piece.position.x][piece.position.y] = piece.getPieceValue();
		}
		return board;
	}
}
