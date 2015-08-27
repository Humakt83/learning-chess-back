package fi.ukkosnetti.chess.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
}
