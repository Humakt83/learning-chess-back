package fi.ukkosnetti.chess.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Move;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.logic.object.Piece;
import fi.ukkosnetti.chess.logic.object.PieceFactory;

public final class MoveUtil {

	private final static int MIN_COORD = 0, MAX_COORD = 7;
	
	private static boolean MATE_CHECK_LOCK = false;
	
	public static List<Move> getDiagonalMoves(Board board, Piece piece) {
		List<Move> moves = new ArrayList<>();
		moves.addAll(getMovesUntilBlocked(board, 1, 1, piece));
		moves.addAll(getMovesUntilBlocked(board, -1, -1, piece));
		moves.addAll(getMovesUntilBlocked(board, 1, -1, piece));
		moves.addAll(getMovesUntilBlocked(board, -1, 1, piece));
		return moves;
	}
	
	public static List<Move> getHorizontalAndVerticalMoves(Board board, Piece piece) {
		List<Move> moves = new ArrayList<>();
		moves.addAll(getMovesUntilBlocked(board, 0, 1, piece));
		moves.addAll(getMovesUntilBlocked(board, 0, -1, piece));
		moves.addAll(getMovesUntilBlocked(board, 1, 0, piece));
		moves.addAll(getMovesUntilBlocked(board, -1, 0, piece));
		return moves;
	}
	
	public static List<Board> filterAndTransformMoves(List<Move> moves) {
		return moves.stream().filter(Objects::nonNull).filter(MoveUtil::isMoveOnBoard).filter(MoveUtil::moveDoesNotCollideWithOwnPiece).map(MoveUtil::transformMove).filter(MoveUtil::moveDoesNotCauseMate).collect(Collectors.toList());
	}
	
	public static boolean isPositionInsideBoard(Position pos) {
		return pos.x >= MIN_COORD && pos.x <= MAX_COORD && pos.y >= MIN_COORD && pos.y <= MAX_COORD;
	}
	
	public static List<Piece> getPieces(Board board, boolean whitePieces) {
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

	private static boolean isMoveOnBoard(Move move) {
		return isPositionInsideBoard(move.position);
	}
	
	private static boolean moveDoesNotCollideWithOwnPiece(Move move) {
		boolean whitePiece = move.piece.whitePiece;
		int slot = move.originalBoard.getSlot(move.position);
		return (slot <= 0 && whitePiece) || (slot >= 0 && !whitePiece);
	}
	
	private static boolean moveDoesNotCauseMate(final Board board) {
		boolean noMate = true;
		if (!MATE_CHECK_LOCK) {
			final int kingToFind = board.turnOfWhite ? -6 : 6;
			MATE_CHECK_LOCK = true;
			noMate = !getPieces(board, board.turnOfWhite)
				.stream()
				.map(piece -> piece.getMoves(board))
				.flatMap(l -> l.stream())
				.filter(futureBoard -> Stream.of(futureBoard.board).flatMap(Stream::of).filter(i -> i == kingToFind).findFirst().orElse(null) == null)
				.findFirst()
				.isPresent();
			MATE_CHECK_LOCK = false;
		}
		return noMate;
	}

	private static Board transformMove(Move move) {
		Integer[][] b = copyBoard(move.originalBoard.board);
		b[move.position.y][move.position.x] = move.piece.getPieceValue();
		b[move.original.y][move.original.x] = 0;
		Board board = new Board(b, !move.originalBoard.turnOfWhite, move, move.newCastlingState);
		if (move.consumer != null) move.consumer.accept(board);
		return board;
	}

	private static Integer[][] copyBoard(Integer[][] board) {
		Integer[][] copy = new Integer[MAX_COORD + 1][MAX_COORD + 1];
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				copy[y][x] = board[y][x];
			}
		}
		return copy;
	}

	private static List<Move> getMovesUntilBlocked(Board board, int xModifier, int yModifier, Piece piece) {
		List<Move> moves = new ArrayList<>();
		boolean blocked = false;
		Position newPosition = piece.position.newPosition(xModifier, yModifier);
		while (MoveUtil.isPositionInsideBoard(newPosition) && !blocked) {
			moves.add(new Move(piece.position, newPosition, piece, board));
			blocked = blocked || board.getSlot(newPosition) != 0;
			newPosition = newPosition.newPosition(xModifier, yModifier);
		}
		return moves;
	}
	


}
