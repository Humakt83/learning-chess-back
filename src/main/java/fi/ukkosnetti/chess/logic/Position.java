package fi.ukkosnetti.chess.logic;

public class Position {

	public final int x, y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position newPosition(int xModifier, int yModifier) {
		return new Position(x + xModifier, y + yModifier);
	}
}
