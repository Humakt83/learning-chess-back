package fi.ukkosnetti.chess.autoplay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fi.ukkosnetti.chess.controller.ChessController;
import fi.ukkosnetti.chess.controller.GameOverController;
import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.GameResult;
import fi.ukkosnetti.chess.logic.MovePicker;
import fi.ukkosnetti.chess.rules.MoveUtil;

@Component
public class Autoplayer {
	
	private static final int MAX_TURNS = 300;

	@Autowired
	private ChessController controller;
	
	@Autowired
	private GameOverController gameOverController;
	
	@Autowired
	private MovePicker picker;
	
	@PostConstruct
	public void startAutoplay() {
		Executors.newFixedThreadPool(1).execute(this::autoPlay);
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
