package fi.ukkosnetti.chess.autoplay;

import org.springframework.web.client.RestTemplate;

import fi.ukkosnetti.chess.controller.ChessController;
import fi.ukkosnetti.chess.controller.GameOverController;
import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.logic.MovePicker;

public class SparringGame extends AbstractGame {

	private final ChessController controller;
	
	private final String sparringAddress;

	private final RestTemplate template;
	
	public SparringGame(MovePicker picker, GameOverController gameOverController, ChessController controller, String address) {
		super(picker, gameOverController);
		this.controller = controller;
		this.sparringAddress = address;
		template = new RestTemplate();
	}

	@Override
	protected Board doMove(Board board, boolean opponentIsBlack) {
		if ((board.turnOfWhite && opponentIsBlack) || (!board.turnOfWhite && !opponentIsBlack)) {
			return controller.getAIMove(board);
		}
		return template.postForObject(sparringAddress, board, Board.class);
	}

}
