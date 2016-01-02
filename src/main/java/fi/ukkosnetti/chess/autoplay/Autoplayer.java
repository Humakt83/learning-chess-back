package fi.ukkosnetti.chess.autoplay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import fi.ukkosnetti.chess.controller.ChessController;
import fi.ukkosnetti.chess.controller.GameOverController;
import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.GameResult;
import fi.ukkosnetti.chess.logic.MovePicker;
import fi.ukkosnetti.chess.rules.MoveUtil;

@Component
public class Autoplayer {
	
	private static final int MAX_TURNS = 300;
	
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
	
	private RestTemplate template = new RestTemplate();
	
	@PostConstruct
	public void startAutoplay() {
		if (!autoplayDisabled) {
			if (sparringOpponentEnabled) {
				Executors.newFixedThreadPool(1).execute(this::sparringAutoPlay);
			} else {
				Executors.newFixedThreadPool(1).execute(this::autoPlay);
			}
		}
	}
	
	private void autoPlay() {
		int gameCounter = 0;
		try {
			while (true) {
				System.out.println("STARTING NEW GAME-------------------------------------------------------------");
				playGame();
				gameCounter++;
				System.out.println("GAME OVER--------------------------------------------------------GAMES PLAYED: " + gameCounter);
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sparringAutoPlay() {
		int gameCounter = 0;
		try {
			while (true) {
				System.out.println("STARTING NEW SPARRING GAME-------------------------------------------------------");
				playSparringGame(gameCounter % 2 == 0, gameCounter);
				gameCounter++;
				System.out.println("GAME OVER--------------------------------------------------------GAMES PLAYED: " + gameCounter);
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void playSparringGame(boolean sparringIsBlack, int gameCounter) {
		List<Integer[][]> madeMoves = new ArrayList<>();
		Board board = new Board(createStartingBoard(), true);
		do {
			if ((madeMoves.size() % 2 == 0 && sparringIsBlack) || (madeMoves.size() % 2 != 0 && !sparringIsBlack)) {
				board = controller.getAIMove(board);
			} else {
				board = template.postForObject(sparringAddress, board, Board.class);
			}
			board.setDoNotCheckForMate(false);
			madeMoves.add(board.board);
		} while (madeMoves.size() < MAX_TURNS && !isGameOver(board));
		if (madeMoves.size() < MAX_TURNS && didGameEndOnCheckMate(board)) {
			gameOverController.gameOver(new GameResult(madeMoves, !board.turnOfWhite));
		} else {
			System.out.println("DID NOT LOSE GAME + " + gameCounter + " AGAINST SPARRING OPPONENT");
			Thread.yield();
		}
	}

	private void playGame() {
		List<Integer[][]> madeMoves = new ArrayList<>();
		Board board = new Board(createStartingBoard(), true);
		do {
			board = controller.getAIMove(board);
			board.setDoNotCheckForMate(false);
			madeMoves.add(board.board);
		} while (madeMoves.size() < MAX_TURNS && !isGameOver(board));
		if (madeMoves.size() < MAX_TURNS && didGameEndOnCheckMate(board)) {
			gameOverController.gameOver(new GameResult(madeMoves, !board.turnOfWhite));
		}
	}
	
	private boolean isGameOver(Board board) {
		return picker.getMove(board) == null;
	}
	
	private boolean didGameEndOnCheckMate(Board board) {
		return MoveUtil.getPieces(board, !board.turnOfWhite)
				.stream()
				.filter(piece -> piece.canEatKing(board))
				.findAny()
				.isPresent();
	}

	private static Integer[][] createStartingBoard() {
		return new Integer[][] {
				{ -4, -2, -3, -5, -6, -3, -2, -4 },
				{ -1, -1, -1, -1, -1, -1, -1, -1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 4, 2, 3, 5, 6, 3, 2, 4 }
		};
	}	
}
