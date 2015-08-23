package fi.ukkosnetti.chess;

import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fi.ukkosnetti.chess.dto.Board;

@RestController
public class ChessController {

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN)
	String alive() {
		return "Alive!";
    }

	@RequestMapping(value = "/aimove", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Board getAIMove(@RequestBody Board board) {
		return new Board(board.board, !board.turnOfWhite);
	}
}
