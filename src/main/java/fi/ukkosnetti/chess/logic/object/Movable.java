package fi.ukkosnetti.chess.logic.object;

import java.util.List;

import fi.ukkosnetti.chess.dto.Board;

public interface Movable {

	List<Board> getMoves();
}
