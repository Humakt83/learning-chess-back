package fi.ukkosnetti.chess.logic.object;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fi.ukkosnetti.chess.dto.Position;


public class PieceFactoryTest {

	@Test
	public void createsPawn() {
		assertTrue(PieceFactory.createPiece(1, new Position(0, 0)) instanceof Pawn);
		assertTrue(PieceFactory.createPiece(-1, new Position(0, 0)) instanceof Pawn);
	}
	
	@Test
	public void createsKnight() {
		assertTrue(PieceFactory.createPiece(2, new Position(0, 0)) instanceof Knight);
		assertTrue(PieceFactory.createPiece(-2, new Position(0, 0)) instanceof Knight);
	}
	
	@Test
	public void createsBishop() {
		assertTrue(PieceFactory.createPiece(3, new Position(0, 0)) instanceof Bishop);
		assertTrue(PieceFactory.createPiece(-3, new Position(0, 0)) instanceof Bishop);
	}
	
	@Test
	public void createsRook() {
		assertTrue(PieceFactory.createPiece(4, new Position(0, 0)) instanceof Rook);
		assertTrue(PieceFactory.createPiece(-4, new Position(0, 0)) instanceof Rook);
	}
	
	@Test
	public void createsQueen() {
		assertTrue(PieceFactory.createPiece(5, new Position(0, 0)) instanceof Queen);
		assertTrue(PieceFactory.createPiece(-5, new Position(0, 0)) instanceof Queen);
	}
	
	@Test
	public void createsKing() {
		assertTrue(PieceFactory.createPiece(6, new Position(0, 0)) instanceof King);
		assertTrue(PieceFactory.createPiece(-6, new Position(0, 0)) instanceof King);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void throwsExceptionForUnknownPieceValue() {
		PieceFactory.createPiece(9, new Position(5, 5));
	}
	
	
}
