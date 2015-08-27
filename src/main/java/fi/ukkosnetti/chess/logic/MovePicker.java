package fi.ukkosnetti.chess.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.logic.object.Piece;
import fi.ukkosnetti.chess.logic.object.PieceFactory;

@Component
public class MovePicker {

	public Board getMove(Board board) {
		List<Board> moves = generateMoves(board);
		return pickRandomMove(moves);
	}

	private Board pickRandomMove(List<Board> moves) {
		System.out.println(moves.size());
		Random random = new Random(System.currentTimeMillis());
		return moves.get(random.nextInt(moves.size() - 1));
	}

	private List<Board> generateMoves(Board board) {
		List<Piece> pieces = board.turnOfWhite ? getWhitePieces(board) : getBlackPieces(board);
		return pieces.stream().map(piece -> piece.getMoves(board)).flatMap(move -> move.stream()).collect(Collectors.toList());
	}

	private List<Piece> getBlackPieces(Board board) {
		return getPieces(board, false);
	}
	
	private List<Piece> getWhitePieces(Board board) {
		return getPieces(board, true);
	}

	private List<Piece> getPieces(Board board, boolean whitePieces) {
		List<Piece> pieces = new ArrayList<>();
		for (int y = 0; y < board.board.length ; y++) {
			for (int x = 0; x < board.board[y].length; x++) {
				Position position = new Position(x, y);
				int slot = board.getSlot(position);
				if ((whitePieces && slot > 0) || (!whitePieces && slot < 0)) {
					pieces.add(PieceFactory.createPiece(slot, position));
				}
			}
		}
		return pieces;
	}

}
