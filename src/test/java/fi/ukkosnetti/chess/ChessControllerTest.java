package fi.ukkosnetti.chess;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

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

import fi.ukkosnetti.chess.dto.Board;

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
		given().contentType(MediaType.APPLICATION_JSON).body(new Board(null, true)).post("/aimove").then().body("turnOfWhite", is(false));
		given().contentType(MediaType.APPLICATION_JSON).body(new Board(null, false)).post("/aimove").then().body("turnOfWhite", is(true));
	}
}