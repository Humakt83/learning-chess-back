package fi.ukkosnetti.chess.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.ukkosnetti.chess.dto.GameResult;
import fi.ukkosnetti.chess.service.BoardService;

@Controller
public class GameOverController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "/gameover", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
	public @ResponseBody void gameOver(@RequestBody GameResult gameResult) {
		boardService.saveGameResult(gameResult);		
	}
}
