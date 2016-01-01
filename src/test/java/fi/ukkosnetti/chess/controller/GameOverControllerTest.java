package fi.ukkosnetti.chess.controller;

import static com.jayway.restassured.RestAssured.given;

import java.util.Arrays;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;

import fi.ukkosnetti.chess.ChessApplication;
import fi.ukkosnetti.chess.dto.GameResult;
import fi.ukkosnetti.chess.test.util.BoardTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ChessApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port:0",
"spring.datasource.url:jdbc:h2:mem:learning-chess-back;DB_CLOSE_ON_EXIT=FALSE"})
public class GameOverControllerTest {

	@Value("${local.server.port}")
	private int port;

	@Before
	public void setUp() throws Exception {
		RestAssured.port = port;
	}
	
	@Test
	public void acceptsWhiteWin() {
		given().contentType(MediaType.APPLICATION_JSON)
			.body(new GameResult(Arrays.asList(BoardTestUtil.createStartingBoard(), BoardTestUtil.createEmptyBoard()), true))
			.post("/gameover")
			.then()
			.statusCode(200);
	}
	
	@Test
	public void acceptsBlackWin() {
		given().contentType(MediaType.APPLICATION_JSON)
		.body(new GameResult(Arrays.asList(BoardTestUtil.createStartingBoard(), BoardTestUtil.createEmptyBoard()), false))
		.post("/gameover")
		.then()
		.statusCode(200);
	}
	
}
