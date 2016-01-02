package fi.ukkosnetti.chess.autoplay;

import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fi.ukkosnetti.chess.controller.ChessController;
import fi.ukkosnetti.chess.controller.GameOverController;
import fi.ukkosnetti.chess.logic.MovePicker;

@Component
public class Autoplayer {
	
	@Value("${chess.disableAutoplay:false}")
	private boolean autoplayDisabled = false;
	
	@Value("${chess.sparring.enabled:false}")
	private boolean sparringOpponentEnabled = false;
	
	@Value("${chess.sparring.address:}")
	private String sparringAddress;

	@Autowired
	private ChessController controller;
	
	@Autowired
	private GameOverController gameOverController;
	
	@Autowired
	private MovePicker picker;
	
	@PostConstruct
	public void startAutoplay() {
		if (!autoplayDisabled) {
			AutoGame game = null;
			if (sparringOpponentEnabled) {
				game = new SparringGame(picker, gameOverController, controller, sparringAddress);
			} else {
				game = new LearningGame(picker, gameOverController, controller);
			}
			Executors.newFixedThreadPool(1).execute(game::autoPlay);
		}
	}

}
