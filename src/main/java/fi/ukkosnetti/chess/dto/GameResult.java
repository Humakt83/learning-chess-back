package fi.ukkosnetti.chess.dto;

import java.util.List;

public class GameResult {
	
	public final List<Integer[][]> boardStates;
	
	public final Boolean winnerIsWhite;

	public GameResult(List<Integer[][]> boardStates, Boolean winnerIsWhite) {
		this.boardStates = boardStates;
		this.winnerIsWhite = winnerIsWhite;
	}
	
	@SuppressWarnings("unused")
	private GameResult() {
		this(null, null);
	}

	@Override
	public String toString() {
		return "GameResult [boardStates=" + boardStates + ", winnerIsWhite=" + winnerIsWhite + "]";
	}

}
