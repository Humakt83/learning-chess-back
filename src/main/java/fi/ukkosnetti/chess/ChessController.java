package fi.ukkosnetti.chess;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChessController {

    @RequestMapping("/")
	String alive() {
		return "Alive!";
    }
}
