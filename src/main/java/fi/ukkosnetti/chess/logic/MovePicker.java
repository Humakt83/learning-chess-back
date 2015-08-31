package fi.ukkosnetti.chess.logic;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.logic.object.Piece;

@Component
public class MovePicker {

	public Board getMove(Board board) {
		List<Board> moves = generateMoves(board);
		return moves.size() > 0 ? pickRandomMove(moves) : null;
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
