package fi.ukkosnetti.chess.logic;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.logic.object.Piece;
import fi.ukkosnetti.chess.model.BoardEntity;
import fi.ukkosnetti.chess.service.BoardService;

@Component
public class MovePicker {
	
	@Autowired
	private BoardService boardService;

	public Board getMove(Board board) {
		List<Board> moves = generateMoves(board);
		return moves.size() > 0 ? pickBestMove(moves, board.turnOfWhite) : null;
	}
	
	private Board pickBestMove(List<Board> moves, boolean turnOfWhite) {
		return turnOfWhite ? pickBestMoveForWhite(moves) : pickBestMoveForBlack(moves);
	}
	
	private Board pickBestMoveForBlack(List<Board> moves) {
		return moves.stream().map(board -> {
			board.setValue(boardService.getBoardEntity(board.board).orElse(new BoardEntity()).getValue());
			return board;
		}).min(compareBoards()).orElse(pickEvaluatedMoveForBlack(moves));
	}


	private Board pickBestMoveForWhite(List<Board> moves) {
		return moves.stream().map(board -> {
			board.setValue(boardService.getBoardEntity(board.board).orElse(new BoardEntity()).getValue());
			return board;
		}).max(compareBoards()).orElse(pickEvaluatedMoveForWhite(moves));
	}
	
	private Board pickEvaluatedMoveForBlack(List<Board> moves) {
		System.out.println("Evaluating black");
		return moves.stream()
				.map(BoardEvaluator::evaluateBoard)
				.min(compareEvaluatedBoards())
				.orElse(pickRandomMove(moves));
	}
	
	private Board pickEvaluatedMoveForWhite(List<Board> moves) {
		return moves.stream()
				.map(BoardEvaluator::evaluateBoard)
				.max(compareEvaluatedBoards())
				.orElse(pickRandomMove(moves));
	}
	
	private Board pickRandomMove(List<Board> moves) {
		System.out.println("Random");
		Random random = new Random(System.currentTimeMillis());
		return moves.get(random.nextInt(moves.size()));
	}
	
	private List<Board> generateMoves(Board board) {
		List<Piece> pieces = board.turnOfWhite ? getWhitePieces(board) : getBlackPieces(board);
		return pieces.stream().map(piece -> piece.getMoves(board)).flatMap(move -> move.stream()).collect(Collectors.toList());
	}

	private List<Piece> getBlackPieces(Board board) {
		return MoveUtil.getPieces(board, false);
	}
	
	private List<Piece> getWhitePieces(Board board) {
		return MoveUtil.getPieces(board, true);
	}
	
	private Comparator<? super Board> compareBoards() {
		return (b1, b2) -> b1.getValue().compareTo(b2.getValue());
	}
	
	private Comparator<? super Board> compareEvaluatedBoards() {
		return (b1, b2) -> b1.getEvaluatedValue().compareTo(b2.getEvaluatedValue());
	}
	
	public static void main(String ... args) {
		MovePicker picker = new MovePicker();
		List<Long> l = Arrays.asList(4l, 3l, 2l, -1l);
		System.out.println(l.stream().max(Long::compareTo).orElse(null));
		System.out.println(l.stream().min(Long::compareTo).orElse(null));
		Board board = new Board(null, true);
		board.setEvaluatedValue(400l);
		Board board2 = new Board(null, true);
		board2.setEvaluatedValue(-200l);
		List<Board> boards = Arrays.asList(board, board2);
		System.out.println(board == boards.stream().max(picker.compareEvaluatedBoards()).get());
		System.out.println(board2 == boards.stream().min(picker.compareEvaluatedBoards()).get());
	}

}
