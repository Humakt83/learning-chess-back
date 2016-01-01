package fi.ukkosnetti.chess.controller;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

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
import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.test.util.BoardTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ChessApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port:0",
        "spring.datasource.url:jdbc:h2:mem:learning-chess-back;DB_CLOSE_ON_EXIT=FALSE"})
public class ChessControllerTest {

    @Value("${local.server.port}")
	private int port;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;
    }

    @Test
	public void applicationIsRunning() throws Exception {
		when().get("/").then().body(is("Alive!"));
    }

	@Test
	public void returnsMoveWherePlayerIsOpposite() throws Exception {
		given().contentType(MediaType.APPLICATION_JSON).body(new Board(BoardTestUtil.createStartingBoard(), true)).post("/aimove").then().body("turnOfWhite", is(false));
		given().contentType(MediaType.APPLICATION_JSON).body(new Board(BoardTestUtil.createStartingBoard(), false)).post("/aimove").then().body("turnOfWhite", is(true));
	}
	
	@Test
	public void returnsRandomMove() throws Exception {
		Integer[][] board = BoardTestUtil.createStartingBoard();
		Board resultBoard = given().contentType(MediaType.APPLICATION_JSON).body(new Board(board, true)).post("/aimove").then().extract().as(Board.class);
		assertThat(resultBoard.board, not(equalTo(board)));
	}
	
	@Test
	public void playsWithItself() throws Exception {
		Integer[][] board = BoardTestUtil.createStartingBoard();
		Board resultBoard = given().contentType(MediaType.APPLICATION_JSON).body(new Board(board, true)).post("/aimove").then().extract().as(Board.class);
		for (int i = 0; i < 10; i++) {
			System.out.println("------------------------------------------------------------------------------------");
			Board previousResult = resultBoard;
			resultBoard = given().contentType(MediaType.APPLICATION_JSON).body(resultBoard).post("/aimove").then().extract().as(Board.class);
			assertThat(resultBoard.board, not(equalTo(previousResult.board)));
		}
	}
}