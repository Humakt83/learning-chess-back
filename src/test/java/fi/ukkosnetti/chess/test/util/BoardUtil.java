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
	
	public static Integer[][] createStartingBoard() {
		return new Integer[][] {
				{ -4, -2, -3, -5, -6, -3, -2, -4 },
				{ -1, -1, -1, -1, -1, -1, -1, -1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 4, 2, 3, 5, 6, 3, 2, 4 }
		};
	}

	public static Integer[][] createBoardWithPieces(Piece... pieces) {
		Integer[][] board = createEmptyBoard();
		for (Piece piece : pieces) {
			board[piece.position.y][piece.position.x] = piece.getPieceValue();
		}
		return board;
	}
}
