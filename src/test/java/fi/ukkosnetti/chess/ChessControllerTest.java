package fi.ukkosnetti.chess;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

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

	/**
	 * @Test public void testCalc() throws Exception { given().param("left", 100)
	 *       .param("right", 200) .get("/calc") .then() .body("left", is(100))
	 *       .body("right", is(200)) .body("answer", is(300)); }
	 **/
}