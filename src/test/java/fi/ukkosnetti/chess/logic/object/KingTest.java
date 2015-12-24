package fi.ukkosnetti.chess.logic.object;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.CastlingState;
import fi.ukkosnetti.chess.dto.CastlingState.CastlingBlocker;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.test.util.BoardUtil;


public class KingTest {

	@Test
	public void hasEightMovesInCenter() {
		Piece king = new King(true, new Position(4, 4));
		assertEquals(8, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king), true)).size());
	}
	
	@Test
	public void hasOnlyThreeMovesInCorner() {
		Piece king = new King(true, new Position(0, 0));
		assertEquals(3, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king), true)).size());
	}
	
	@Test
	public void whiteKingCanCastleToLeft() {
		Piece king = new King(true, new Position(4, 7));
		assertEquals(6, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(0, 7))), true)).size());
	}
	
	@Test
	public void blackKingCanCastleToLeft() {
		Piece king = new King(false, new Position(4, 0));
		assertEquals(6, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(false, new Position(0, 0))), false)).size());
	}
	
	@Test
	public void whiteKingCanCastleToRight() {
		Piece king = new King(true, new Position(4, 7));
		assertEquals(6, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(7, 7))), true)).size());
	}
	
	@Test
	public void blackKingCanCastleToRight() {
		Piece king = new King(false, new Position(4, 0));
		assertEquals(6, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(false, new Position(7, 0))), false)).size());
	}
	
	@Test
	public void cannotCastleWhenKingHasMoved() {
		Piece king = new King(true, new Position(4, 7));
		CastlingState state = new CastlingState();
		state.addBlocker(CastlingBlocker.WHITE_KING_MOVED);
		assertEquals(5, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(0, 7))), true, null, state)).size());
	}
	
	@Test
	public void cannotCastleWhenRookHasMoved() {
		Piece king = new King(true, new Position(4, 7));
		CastlingState state = new CastlingState();
		state.addBlocker(CastlingBlocker.WHITE_LEFT_ROOK_MOVED);
		assertEquals(5, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(0, 7))), true, null, state)).size());
	}
	
	@Test
	public void cannotCastleWhenSomethingIsBetweenKingAndRook() {
		Piece king = new King(true, new Position(4, 7));
		assertEquals(5, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(7, 7)), new Knight(true, new Position(6,7))), true)).size());
		assertEquals(4, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(7, 7)), new Bishop(true, new Position(5,7))), true)).size());
		assertEquals(5, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(0, 7)), new Bishop(true, new Position(2,7))), true)).size());
		assertEquals(5, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(0, 7)), new Knight(true, new Position(1,7))), true)).size());
		assertEquals(4, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(0, 7)), new Queen(true, new Position(3,7))), true)).size());
	}
	
	@Test
	public void cannotCastleWhenLineIsThreatened() {
		Piece king = new King(true, new Position(4, 7));
		assertEquals(3, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(7, 7)), new Rook(false, new Position(5, 0))), true)).size());
		assertEquals(5, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(7, 7)), new Rook(false, new Position(6, 0))), true)).size());
		assertEquals(3, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(0, 7)), new Rook(false, new Position(3, 0))), true)).size());
		assertEquals(5, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(0, 7)), new Rook(false, new Position(2, 0))), true)).size());
		assertEquals(5, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(0, 7)), new Rook(false, new Position(1, 0))), true)).size());
	}
	
}
