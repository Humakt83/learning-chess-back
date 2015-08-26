package fi.ukkosnetti.chess.logic.object;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.CastlingState;
import fi.ukkosnetti.chess.dto.CastlingState.CastlingBlocker;
import fi.ukkosnetti.chess.logic.Position;
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
		assertEquals(6, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(false, new Position(0, 0))), true)).size());
	}
	
	@Test
	public void whiteKingCanCastleToRight() {
		Piece king = new King(true, new Position(4, 7));
		assertEquals(6, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(true, new Position(7, 7))), true)).size());
	}
	
	@Test
	public void blackKingCanCastleToRight() {
		Piece king = new King(false, new Position(4, 0));
		assertEquals(6, king.getMoves(new Board(BoardUtil.createBoardWithPieces(king, new Rook(false, new Position(7, 0))), true)).size());
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
	
}
