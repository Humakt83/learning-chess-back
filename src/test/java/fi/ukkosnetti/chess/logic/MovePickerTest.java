package fi.ukkosnetti.chess.logic;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.test.util.BoardUtil;


public class MovePickerTest {

	private MovePicker picker = new MovePicker();
	
	private final static Integer[][] STARTING_BOARD = BoardUtil.createStartingBoard();
	
	@Test
	public void picksMoveForWhite() {
		Board board = picker.getMove(new Board(STARTING_BOARD, true));
		assertNotNull(board);
		assertFalse(board.turnOfWhite);
		assertFalse(STARTING_BOARD.equals(board));
	}
	
	@Test
	public void picksMoveForBlack() {
		Board board = picker.getMove(new Board(STARTING_BOARD, false));
		assertNotNull(board);
		assertTrue(board.turnOfWhite);
		assertFalse(STARTING_BOARD.equals(board));
	}
	
	@Test
	public void hasNoMovesToPickForWhiteOnCheckmate() {
		Integer[][] boardState = new Integer[][] {
				{ -4, 6, 0, -5, -6, -3, -2, -4 },
				{ -1, 0, -1, -1, -1, -1, -1, -1 },
				{ 0, 0, -3, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 0, 1, 1, 1 },
				{ 4, 2, 3, 5, 0, 3, 2, 4 }
		};
		assertNull(picker.getMove(new Board(boardState, true)));
	}
	
	@Test
	public void hasNoMovesToPickForBlackOnCheckmate() {
		Integer[][] boardState = new Integer[][] {
				{ -4, 0, 0, -5, 0, -3, -2, -4 },
				{ -1, 0, -1, -1, -1, -1, -1, -1 },
				{ 0, 0, -3, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 1 },
				{ 0, 0, 0, 0, 2, 0, 0, -6 },
				{ 1, 1, 1, 1, 0, 1, 0, 0 },
				{ 4, 2, 3, 5, 6, 3, 0, 4 }
		};
		assertNull(picker.getMove(new Board(boardState, false)));
	}
	
	@Test
	public void hasOneMoveToPickForBlackOnCheck() {
		final Integer[][] expectedBoardState = new Integer[][] {
				{ 0, 0, 0, 0, 0, 0, 0, -3 },
				{ 0, 0, -6, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ -1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 1, 0, 0, -1 },
				{ 0, 0, -2, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, -5, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 6, 0, 2 }
		};
		Integer[][] boardState = new Integer[][] {
				{ 0, 0, 0, 0, 0, 0, 0, -3 },
				{ 0, 0, 5, 0, 0, 0, 0, 0 },
				{ 0, 0, -6, 0, 0, 0, 0, 0 },
				{ -1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 1, 0, 0, -1 },
				{ 0, 0, -2, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, -5, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 6, 0, 2 }
		};
		assertArrayEquals(expectedBoardState, picker.getMove(new Board(boardState, false)).board);
	}
}
