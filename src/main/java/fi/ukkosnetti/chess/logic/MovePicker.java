package fi.ukkosnetti.chess.logic;

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
		Board bestMove = turnOfWhite ? pickBestMoveForWhite(moves) : pickBestMoveForBlack(moves);
		return bestMove.getValue() != 0 ? bestMove : pickRandomMove(moves);
	}
	
	private Board pickBestMoveForBlack(List<Board> moves) {
		return moves.stream().map(board -> {
			board.setValue(boardService.getBoardEntity(board.board).orElse(new BoardEntity()).getValue());
			return board;
		}).min((b1, b2) -> b1.getValue().compareTo(b2.getValue())).orElse(pickRandomMove(moves));
	}	

	private Board pickBestMoveForWhite(List<Board> moves) {
		return moves.stream().map(board -> {
			board.setValue(boardService.getBoardEntity(board.board).orElse(new BoardEntity()).getValue());
			return board;
		}).max((b1, b2) -> b1.getValue().compareTo(b2.getValue())).orElse(pickRandomMove(moves));
	}

	private Board pickRandomMove(List<Board> moves) {
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

}
