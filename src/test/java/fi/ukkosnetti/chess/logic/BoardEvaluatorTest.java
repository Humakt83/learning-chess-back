package fi.ukkosnetti.chess.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.test.util.BoardUtil;

public class BoardEvaluatorTest {

	@Test
	public void boardScoreIsEven() {
		Board board = givenBoard(BoardUtil.createStartingBoard());
		BoardEvaluator.evaluateBoard(board);
		assertEquals(0, board.getEvaluatedValue().longValue());		
	}
	
	@Test
	public void boardScoreFavorsWhite() {
		Board board = givenBoard(new Integer[][] {
			{ -4, -2, -3, -5, -6, -3, -2, 0 },
			{ -1, -1, -1, -1, -1, -1, -1, -1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 4, 2, 3, 5, 6, 3, 2, 4 }});
		BoardEvaluator.evaluateBoard(board);
		assertTrue(0l < board.getEvaluatedValue().longValue());		
	}
	
	@Test
	public void boardScoreFavorsBlack() {
		Board board = givenBoard(new Integer[][] {
			{ -4, -2, -3, -5, -6, -3, -2, 0 },
			{ -1, -1, -1, -1, -1, -1, -1, -1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 4, 0, 0, 5, 6, 3, 2, 4 }});
		BoardEvaluator.evaluateBoard(board);
		assertTrue(0l > board.getEvaluatedValue().longValue());		
	}
	
	private Board givenBoard(Integer[][] board) {
		return new Board(board, true);
	}
	
}
