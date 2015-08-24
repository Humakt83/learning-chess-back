package fi.ukkosnetti.chess.logic.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.logic.Position;
import fi.ukkosnetti.chess.test.util.BoardUtil;


public class RookTest {

	@Test
	public void has14MovesInCenter() {
		Piece rook = new Rook(true, new Position(4, 4));
		assertEquals(14, rook.getMoves(new Board(BoardUtil.createBoardWithPieces(rook), true)).size());
	}
	
	@Test
	public void has14MovesInCorner() {
		Piece rook = new Rook(true, new Position(0, 0));
		assertEquals(14, rook.getMoves(new Board(BoardUtil.createBoardWithPieces(rook), true)).size());
	}
	
	@Test
	public void hasOnlyTwoMovesWhenBlockedByEnemiesInCorner() {
		Piece rook = new Rook(true, new Position(0, 0));
		assertEquals(2, rook.getMoves(new Board(BoardUtil.createBoardWithPieces(rook, new Pawn(false, new Position(1, 0)), new Pawn(false, new Position(0, 1))), true)).size());
	}
	
	@Test
	public void hasNoMovesWhenBlockedByAlliesInCorner() {
		Piece rook = new Rook(true, new Position(0, 0));
		assertTrue(rook.getMoves(new Board(BoardUtil.createBoardWithPieces(rook, new Pawn(true, new Position(1, 0)), new Pawn(true, new Position(0, 1))), true)).isEmpty());
	}
	
}
