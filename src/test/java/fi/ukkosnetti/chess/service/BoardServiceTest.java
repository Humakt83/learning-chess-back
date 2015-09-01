package fi.ukkosnetti.chess.service;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fi.ukkosnetti.chess.dto.GameResult;
import fi.ukkosnetti.chess.model.BoardEntity;
import fi.ukkosnetti.chess.repository.BoardEntityRepository;
import fi.ukkosnetti.chess.test.util.BoardUtil;

@RunWith(MockitoJUnitRunner.class)
public class BoardServiceTest {

	@Mock
	private BoardEntityRepository repository;
	
	@InjectMocks
	private BoardService service;
	
	@Test
	public void getsBoardEntity() {
		service.getBoardEntity(BoardUtil.createStartingBoard());
		verify(repository).findOne("-4-2-3-5-6-3-2-4-1-1-1-1-1-1-1-1000000000000000000000000000000001111111142356324");
	}
	
	@Test
	public void savesGameResult() {
		service.saveGameResult(new GameResult(Arrays.asList(BoardUtil.createStartingBoard(), BoardUtil.createEmptyBoard()), true));
		verify(repository, times(2)).save(isA(BoardEntity.class));
	}
}
