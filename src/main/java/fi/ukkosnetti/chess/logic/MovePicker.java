package fi.ukkosnetti.chess.logic;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

	private Board pickBestMoveForBlack(final List<Board> moves) {
		Optional<Board> bestMove =  moves.stream().map(board -> {
			board.setValue(boardService.getBoardEntity(board.board).orElse(new BoardEntity()).getValue());
			return board;
		}).min(compareBoards());
		return bestMove.isPresent() && bestMove.get().getValue() < 0 ? bestMove.get() : pickEvaluatedMoveForBlack(moves);
	}


	private Board pickBestMoveForWhite(final List<Board> moves) {
		Optional<Board> bestMove =  moves.stream().map(board -> {
			board.setValue(boardService.getBoardEntity(board.board).orElse(new BoardEntity()).getValue());
			return board;
		}).max(compareBoards());
		return bestMove.isPresent() && bestMove.get().getValue() > 0 ? bestMove.get() : pickEvaluatedMoveForWhite(moves);
	}

	private Board pickEvaluatedMoveForBlack(final List<Board> moves) {
		final Long bestValue = moves.stream()
				.map(BoardEvaluator::evaluateBoard)
				.min(compareEvaluatedBoards())
				.get().getEvaluatedValue();
		return pickRandomMoveFromBestEvaluatedMoves(moves, bestValue);
	}

	private Board pickEvaluatedMoveForWhite(final List<Board> moves) {
		final Long bestValue = moves.stream()
				.map(BoardEvaluator::evaluateBoard)
				.max(compareEvaluatedBoards())
				.get().getEvaluatedValue();
		return pickRandomMoveFromBestEvaluatedMoves(moves, bestValue);
	}

	private Board pickRandomMoveFromBestEvaluatedMoves(final List<Board> moves, final Long bestValue) {
		return pickRandomMove(moves.stream()
				.filter(board -> board.getEvaluatedValue().equals(bestValue))
				.collect(Collectors.toList()));
	}

	private Board pickRandomMove(final List<Board> moves) {
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
		return (b1, b2) -> Long.compare(b1.getValue(), b2.getValue());
	}

	private Comparator<? super Board> compareEvaluatedBoards() {
		return (b1, b2) -> Long.compare(b1.getEvaluatedValue(), b2.getEvaluatedValue());
	}

}
