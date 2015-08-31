package fi.ukkosnetti.chess.logic.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.test.util.BoardUtil;


public class QueenTest {

	@Test
	public void has27MovesInCenter() {
		Piece queen = new Queen(true, new Position(4, 4));
		assertEquals(27, queen.getMoves(new Board(BoardUtil.createBoardWithPieces(queen), true)).size());
	}
	
	@Test
	public void has21MovesInCorner() {
		Piece queen = new Queen(true, new Position(0, 0));
		assertEquals(21, queen.getMoves(new Board(BoardUtil.createBoardWithPieces(queen), true)).size());
	}
	
	@Test
	public void hasOnlyThreeMovesWhenBlockedByEnemiesInCorner() {
		Piece queen = new Queen(true, new Position(0, 0));
		assertEquals(3, queen.getMoves(new Board(BoardUtil.createBoardWithPieces(queen, new Pawn(false, new Position(1, 0)), new Pawn(false, new Position(0, 1)), new Pawn(false, new Position(1, 1)),
				new King(true, new Position(7,7))), true)).size());
	}
	
	@Test
	public void hasNoMovesWhenBlockedByAlliesInCorner() {
		Piece queen = new Queen(true, new Position(0, 0));
		assertTrue(queen.getMoves(new Board(BoardUtil.createBoardWithPieces(queen, new Pawn(true, new Position(1, 0)), new Pawn(true, new Position(0, 1)), new Pawn(true, new Position(1, 1))), true)).isEmpty());
	}
}
