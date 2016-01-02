package fi.ukkosnetti.chess.autoplay;

import fi.ukkosnetti.chess.controller.ChessController;
import fi.ukkosnetti.chess.controller.GameOverController;
import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.logic.MovePicker;

public class LearningGame extends AbstractGame {
	
	private final ChessController controller;
	
	public LearningGame(MovePicker picker, GameOverController gameOverController, ChessController controller) {
		super(picker, gameOverController);
		this.controller = controller;
	}

	@Override
	protected Board doMove(Board board, boolean opponentIsBlack) {
		return controller.getAIMove(board);
	}

}
