package fi.ukkosnetti.chess.logic.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.dto.Move;
import fi.ukkosnetti.chess.dto.MoveBuilder;
import fi.ukkosnetti.chess.dto.Position;
import fi.ukkosnetti.chess.logic.MoveUtil;

public class Pawn extends Piece {

	public Pawn(boolean whitePiece, Position position) {
		super(whitePiece, position, 1);
	}

	@Override
	public List<Board> getMoves(final Board board) {
		List<Move> moves = getMovesForward(board);
		moves.addAll(getDiagonalAttackMoves(board));
		moves.add(getEnPassantMove(board));
		return MoveUtil.filterAndTransformMoves(moves);
	}
	
	private int getDirection() {
		return whitePiece ? -1 : 1;
	}

	private List<Move> getMovesForward(final Board board) {
		List<Move> moves = new ArrayList<Move>();
		Position positionForward = position.newPosition(0, getDirection());
		if (board.getSlot(positionForward) == 0) {
			if (positionForward.y == 0 || positionForward.y == 7) {
				moves.addAll(getLevelUpMoves(board, positionForward));
			} else {
				moves.add(new Move(position, positionForward, this, board));
				Position positionForwardTwice = positionForward.newPosition(0, getDirection());
				if (((position.y == 6 && whitePiece) || (position.y == 1 && !whitePiece)) &&  board.getSlot(positionForwardTwice) == 0) {
					moves.add(new MoveBuilder(position, positionForwardTwice, this, board).setPawnDoubleForward(true).build());
				}
			}
		}
		return moves;
	}
	
	private List<Move> getDiagonalAttackMoves(final Board board) {
		return Arrays.asList(position.newPosition(-1, getDirection()), position.newPosition(1, getDirection()))
				.stream()
				.filter(MoveUtil::isPositionInsideBoard)
				.filter(pos -> {
					int slot = board.getSlot(pos);
					return (slot < 0 && whitePiece) || (slot > 0 && !whitePiece);
				}).map(pos -> new Move(position, pos, this, board)).collect(Collectors.toList());
	}
	
	private List<Move> getLevelUpMoves(final Board board, Position positionForward) {
		return Arrays.asList(
				new Move(position, positionForward, new Knight(whitePiece, positionForward), board),
				new Move(position, positionForward, new Bishop(whitePiece, positionForward), board),
				new Move(position, positionForward, new Rook(whitePiece, positionForward), board),
				new Move(position, positionForward, new Queen(whitePiece, positionForward), board));
	}
	
	private Move getEnPassantMove(final Board board) {
		return Arrays.asList(position.newPosition(-1, getDirection()), position.newPosition(1, getDirection()))
				.stream()
				.filter(MoveUtil::isPositionInsideBoard)
				.filter(p -> board.lastMove != null && board.lastMove.pawnDoubleForward && board.lastMove.position.y == position.y)
				.filter(pos -> board.getSlot(pos) == 0 && board.lastMove.position.x == pos.x)
				.map(pos -> new MoveBuilder(position, pos, this, board).setConsumer(getEnPassantConsumer()).build())
				.findAny().orElse(null);
	}

	private Consumer<Board> getEnPassantConsumer() {
		return board -> board.board[board.lastMove.position.y][board.lastMove.position.x] = 0;
	}
}
