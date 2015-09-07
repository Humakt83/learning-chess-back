package fi.ukkosnetti.chess.logic.object;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.CastlingState;
import fi.ukkosnetti.chess.dto.Move;
import fi.ukkosnetti.chess.dto.MoveBuilder;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.test.util.BoardUtil;


public class PawnTest {
	
	@Test
	public void whitePawnHasTwoMovesWhenOnStartingLine() {
		Piece pawn = new Pawn(true, new Position(0, 6));
		assertEquals(2, pawn.getMoves(new Board(BoardUtil.createBoardWithPieces(pawn), true)).size());
	}
	
	@Test
	public void blackPawnHasTwoMovesWhenOnStartingLine() {
		Piece pawn = new Pawn(false, new Position(0, 1));
		assertEquals(2, pawn.getMoves(new Board(BoardUtil.createBoardWithPieces(pawn), false)).size());
	}
	
	@Test
	public void whitePawnCanMoveForwardOnlyOnceWhenNotOnStartingLine() {
		Piece pawn = new Pawn(true, new Position(2, 4));
		assertEquals(1, pawn.getMoves(new Board(BoardUtil.createBoardWithPieces(pawn), true)).size());
	}

	@Test
	public void blackPawnCanMoveForwardOnlyOnceWhenNotOnStartingLine() {
		Piece pawn = new Pawn(false, new Position(3, 5));
		assertEquals(1, pawn.getMoves(new Board(BoardUtil.createBoardWithPieces(pawn), false)).size());
	}
	
	@Test
	public void whitePawnCannotMoveWhenItIsBlocked() {
		Piece pawn = new Pawn(true, new Position(3, 5));
		assertEquals(0, pawn.getMoves(new Board(BoardUtil.createBoardWithPieces(pawn, new Pawn(false, new Position(3, 4))), true)).size());
	}
	
	@Test
	public void blackPawnCannotMoveWhenItIsBlocked() {
		Piece pawn = new Pawn(false, new Position(3, 5));
		assertEquals(0, pawn.getMoves(new Board(BoardUtil.createBoardWithPieces(pawn, new Pawn(false, new Position(3, 6))), true)).size());
	}
	
	@Test
	public void whitePawnCanDiagonallyAttack() {
		Piece pawn = new Pawn(true, new Position(3, 5));
		assertEquals(2, pawn.getMoves(new Board(BoardUtil.createBoardWithPieces(pawn, new Pawn(false, new Position(2, 4)), 
				new Pawn(false, new Position(4, 4)), new Pawn(false, new Position(3, 4)), new King(true, new Position(7,7))), true)).size());
	}
	
	@Test
	public void blackPawnCanDiagonallyAttack() {
		Piece pawn = new Pawn(false, new Position(3, 5));
		assertEquals(1, pawn.getMoves(new Board(BoardUtil.createBoardWithPieces(pawn, new Pawn(false, new Position(2, 6)), new Pawn(true, new Position(4, 6)), new Pawn(true, new Position(3, 6)),
				new King(false, new Position(0,0))), false)).size());
	}
	
	@Test
	public void enPassant() {
		Piece pawn = new Pawn(true, new Position(1, 3));
		Piece pawnDoubleForward = new Pawn(false, new Position(0, 3));
		Board board = new Board(BoardUtil.createBoardWithPieces(pawn, pawnDoubleForward), false);
		Move previousMove = new MoveBuilder(new Position(0, 1), new Position(0, 3), pawnDoubleForward, board).setPawnDoubleForward(true).build();
		board = new Board(BoardUtil.createBoardWithPieces(pawn, pawnDoubleForward, new King(true, new Position(7,7))), true, previousMove, new CastlingState());
		assertEquals(2, pawn.getMoves(board).size());
	}
	
	@Test
	public void hasLevelupMovesForWhitePawn() {
		Piece pawn = new Pawn(true, new Position(0,1));
		assertEquals(4, pawn.getMoves(new Board(BoardUtil.createBoardWithPieces(pawn), true)).size());
	}
	
	@Test
	public void hasLevelupMovesForBlackPawn() {
		Piece pawn = new Pawn(false, new Position(0,6));
		assertEquals(4, pawn.getMoves(new Board(BoardUtil.createBoardWithPieces(pawn), false)).size());
	}
	
	@Test
	public void diagonalAttackProvidesLevelupForWhite() {
		Piece pawn = new Pawn(true, new Position(0,1));
		assertEquals(4, pawn.getMoves(new Board(BoardUtil.createBoardWithPieces(pawn, new Knight(false, new Position(1, 0)), new Knight(false, new Position(0, 0)), new King(true, new Position(7,7))), true)).size());
	}
	
	@Test
	public void diagonalAttackProvidesLevelupForBlack() {
		Piece pawn = new Pawn(false, new Position(0,6));
		assertEquals(4, pawn.getMoves(new Board(BoardUtil.createBoardWithPieces(pawn, new Knight(true, new Position(1, 7)), new Knight(false, new Position(0, 7)), new King(false, new Position(7,7))), false)).size());
	}

}
