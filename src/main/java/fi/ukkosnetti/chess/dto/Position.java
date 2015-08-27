package fi.ukkosnetti.chess.dto;

public class Position {

	public final int x, y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position newPosition(int xModifier, int yModifier) {
		return new Position(x + xModifier, y + yModifier);
	}
	
	@SuppressWarnings("unused")
	private Position() {
		this(0, 0);
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
	
}
