package fi.ukkosnetti.chess.autoplay;

import java.util.ArrayList;
import java.util.List;

import fi.ukkosnetti.chess.controller.GameOverController;
import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.GameResult;
import fi.ukkosnetti.chess.logic.MovePicker;
import fi.ukkosnetti.chess.rules.MoveUtil;
import fi.ukkosnetti.chess.test.util.BoardTestUtil;

public abstract class AbstractGame implements AutoGame {
	
	private static final int MAX_TURNS = 300;
	
	private final MovePicker picker;
	
	private final GameOverController gameOverController;
	
	protected AbstractGame(MovePicker picker, GameOverController controller) {
		this.picker = picker;
		this.gameOverController = controller;
	}
	
	@Override
	public final void autoPlay() {
		int gameCounter = 0;
		try {
			while (true) {
				System.out.println("STARTING NEW GAME-------------------------------------------------------------");
				playGame(gameCounter);
				gameCounter++;
				System.out.println("GAME OVER--------------------------------------------------------GAMES PLAYED: " + gameCounter);
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void playGame(int gameCounter) {
		boolean opponentIsBlack = gameCounter % 2 == 0;
		System.out.println("GAME ("+ gameCounter + ") STARTED OPPONENT IS BLACK=" + opponentIsBlack);
		List<Integer[][]> madeMoves = new ArrayList<>();
		Board board = new Board(BoardTestUtil.createStartingBoard(), true);
		do {
			board = doMove(board, opponentIsBlack);
			board.setDoNotCheckForMate(false);
			madeMoves.add(board.board);
		} while (madeMoves.size() < MAX_TURNS && !isGameOver(board));
		if (madeMoves.size() < MAX_TURNS && didGameEndOnCheckMate(board)) {
			System.out.println("GAME (" + gameCounter + ") ENDED IN CHECKMATE---------------------------------- WINNER IS WHITE=" 
					+ !board.turnOfWhite + ", BLACK=" + board.turnOfWhite);
			gameOverController.gameOver(new GameResult(madeMoves, !board.turnOfWhite));
		} else {
			System.out.println("GAME (" + gameCounter + ") WAS A TIE!" );
		}
	}
	
	protected abstract Board doMove(Board board, boolean opponentIsBlack);
	
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

}
