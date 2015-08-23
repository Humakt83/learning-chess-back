package fi.ukkosnetti.chess.logic.object;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.logic.Position;
import fi.ukkosnetti.chess.test.util.BoardUtil;


public class KnightTest {

	@Test
	public void hasEightMoves() {
		Piece knight = new Knight(true, new Position(4, 4));
		assertEquals(8, knight.getMoves(new Board(BoardUtil.createBoardWithPieces(knight), true)).size());
	}
	
	@Test
	public void hasOnlyTwoMovesInCorner() {
		Piece knight = new Knight(true, new Position(0, 0));
		assertEquals(2, knight.getMoves(new Board(BoardUtil.createBoardWithPieces(knight), true)).size());
	}
}
