package fi.ukkosnetti.chess.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.logic.MovePicker;

@RestController
public class ChessController {

	@Autowired
	private MovePicker movePicker;
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN)
	public String alive() {
		return "Alive!";
    }

	@RequestMapping(value = "/aimove", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody Board getAIMove(@RequestBody Board board) {
		Board newBoard = movePicker.getMove(board);
		if (newBoard == null) throw new ResourceNotFoundException("No legal moves left");
		return newBoard;		
	}
		
	@SuppressWarnings("serial")
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	class ResourceNotFoundException extends RuntimeException {

		public ResourceNotFoundException(String message) {
			super(message);
		}
	}
	
}
