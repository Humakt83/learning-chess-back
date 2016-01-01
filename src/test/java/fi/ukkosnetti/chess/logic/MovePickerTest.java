package fi.ukkosnetti.chess.logic;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fi.ukkosnetti.chess.dto.Board;
import fi.ukkosnetti.chess.model.BoardEntity;
import fi.ukkosnetti.chess.service.BoardService;
import fi.ukkosnetti.chess.test.util.BoardTestUtil;

@RunWith(MockitoJUnitRunner.class)
public class MovePickerTest {

	@Mock
	private BoardService boardService;
	
	@InjectMocks
	private MovePicker picker;
	
	private final static Integer[][] STARTING_BOARD = BoardTestUtil.createStartingBoard();
	
	@Before
	public void init() {
		when(boardService.getBoardEntity(any())).thenReturn(Optional.ofNullable(null));
	}
	
	@Test
	public void picksMoveForWhite() {
		Board board = picker.getMove(new Board(STARTING_BOARD, true));
		assertNotNull(board);
		assertFalse(board.turnOfWhite);
		assertFalse(STARTING_BOARD.equals(board));
	}
	
	@Test
	public void picksMoveForBlack() {
		Board board = picker.getMove(new Board(STARTING_BOARD, false));
		assertNotNull(board);
		assertTrue(board.turnOfWhite);
		assertFalse(STARTING_BOARD.equals(board));
	}
	
	@Test
	public void hasNoMovesToPickForWhiteOnCheckmate() {
		Integer[][] boardState = new Integer[][] {
				{ -4, 6, 0, -5, -6, -3, -2, -4 },
				{ -1, 0, -1, -1, -1, -1, -1, -1 },
				{ 0, 0, -3, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 0, 1, 1, 1 },
				{ 4, 2, 3, 5, 0, 3, 2, 4 }
		};
		assertNull(picker.getMove(new Board(boardState, true)));
	}
	
	@Test
	public void hasNoMovesToPickForBlackOnCheckmate() {
		Integer[][] boardState = new Integer[][] {
				{ -4, 0, 0, -5, 0, -3, -2, -4 },
				{ -1, 0, -1, -1, -1, -1, -1, -1 },
				{ 0, 0, -3, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 1 },
				{ 0, 0, 0, 0, 2, 0, 0, -6 },
				{ 1, 1, 1, 1, 0, 1, 0, 0 },
				{ 4, 2, 3, 5, 6, 3, 0, 4 }
		};
		assertNull(picker.getMove(new Board(boardState, false)));
	}
	
	@Test
	public void hasOneMoveToPickForBlackOnCheck() {
		final Integer[][] expectedBoardState = new Integer[][] {
				{ 0, 0, 0, 0, 0, 0, 0, -3 },
				{ 0, 0, -6, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ -1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 1, 0, 0, -1 },
				{ 0, 0, -2, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, -5, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 6, 0, 2 }
		};
		Integer[][] boardState = new Integer[][] {
				{ 0, 0, 0, 0, 0, 0, 0, -3 },
				{ 0, 0, 5, 0, 0, 0, 0, 0 },
				{ 0, 0, -6, 0, 0, 0, 0, 0 },
				{ -1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 1, 0, 0, -1 },
				{ 0, 0, -2, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, -5, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 6, 0, 2 }
		};
		assertArrayEquals(expectedBoardState, picker.getMove(new Board(boardState, false)).board);
	}
	
	@Test
	public void picksPreferredMoveForWhite() {
		final Integer[][] bestBoard = new Integer[][] {
				{ -4, -2, -3, -5, -6, -3, -2, -4 },
				{ -1, -1, -1, -1, -1, -1, -1, -1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 0, 1, 1, 1 },
				{ 4, 2, 3, 5, 6, 3, 2, 4 }
		};
		when(boardService.getBoardEntity(argThat(get2DArrayMatcher(bestBoard)))).thenReturn(createBoardEntity(500l));
		assertArrayEquals(bestBoard, picker.getMove(new Board(STARTING_BOARD, true)).board);
	}
	
	@Test
	public void picksPreferredMoveForBlack() {
		final Integer[][] previousBoard = new Integer[][] {
				{ -4, -2, -3, -5, -6, -3, -2, -4 },
				{ -1, -1, -1, -1, -1, -1, -1, -1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 0, 1, 1, 1 },
				{ 4, 2, 3, 5, 6, 3, 2, 4 }
		};
		final Integer[][] bestBoard = new Integer[][] {
				{ -4, -2, -3, -5, -6, -3, -2, -4 },
				{ -1, -1, -1, -1, 0, -1, -1, -1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, -1, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 0, 1, 1, 1 },
				{ 4, 2, 3, 5, 6, 3, 2, 4 }
		};
		when(boardService.getBoardEntity(argThat(get2DArrayMatcher(bestBoard)))).thenReturn(createBoardEntity(-500l));
		assertArrayEquals(bestBoard, picker.getMove(new Board(previousBoard, false)).board);
	}
	
	@Test
	public void doesNotPickMoveThatIsMoreAdvantageousToBlackForWhite() {
		final Integer[][] previousBoard = new Integer[][] {
				{ -6, 0, 0, 0, 0, 0, 0, 0 },
				{ -1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, -1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }
		};
		final Integer[][] worstBoard = new Integer[][] {
				{ -6, 0, 0, 0, 0, 0, 0, 0 },
				{ -1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 6, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, -1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }
		};
		final Integer[][] bestBoard = new Integer[][] {
				{ -6, 0, 0, 0, 0, 0, 0, 0 },
				{ -1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 6, -1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }
		};
		when(boardService.getBoardEntity(argThat(get2DArrayMatcher(worstBoard)))).thenReturn(createBoardEntity(-500l));
		when(boardService.getBoardEntity(argThat(get2DArrayMatcher(bestBoard)))).thenReturn(createBoardEntity(500l));
		assertArrayEquals(bestBoard, picker.getMove(new Board(previousBoard, true)).board);
	}
	
	@Test
	public void doesNotPickMoveThatIsMoreAdvantageousToWhiteForBlack() {
		final Integer[][] previousBoard = new Integer[][] {
				{ -6, 0, 0, 0, 0, 0, 0, 0 },
				{ -1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, -1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }
		};
		final Integer[][] worstBoard = new Integer[][] {
				{ 0, -6, 0, 0, 0, 0, 0, 0 },
				{ -1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, -1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }
		};
		final Integer[][] bestBoard = new Integer[][] {
				{ -6, 0, 0, 0, 0, 0, 0, 0 },
				{ -1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 6, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, -1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }
		};
		when(boardService.getBoardEntity(argThat(get2DArrayMatcher(worstBoard)))).thenReturn(createBoardEntity(500l));
		when(boardService.getBoardEntity(argThat(get2DArrayMatcher(bestBoard)))).thenReturn(createBoardEntity(-500l));
		assertArrayEquals(bestBoard, picker.getMove(new Board(previousBoard, false)).board);
	}
	
	private Optional<BoardEntity> createBoardEntity(long value) {
		BoardEntity entity = new BoardEntity();
		entity.setValue(value);
		return Optional.of(entity);
	}
	
	private Matcher<Integer[][]> get2DArrayMatcher(final Integer[][] arrayToMatch) {
		return new BaseMatcher<Integer[][]>() {

			@Override
			public boolean matches(Object item) {
				return Arrays.deepEquals((Integer[][]) item, arrayToMatch);
			}

			@Override
			public void describeTo(Description description) {
			}
		};
	}
}
