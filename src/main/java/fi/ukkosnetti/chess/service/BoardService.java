package fi.ukkosnetti.chess.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ukkosnetti.chess.dto.GameResult;
import fi.ukkosnetti.chess.model.BoardEntity;
import fi.ukkosnetti.chess.repository.BoardEntityRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardEntityRepository boardRepository;
	
	public Optional<BoardEntity> getBoardEntity(Integer[][] board) {
		return getBoardEntity(convertBoardToString(board));
	}
	
	@Transactional
	public void saveGameResult(final GameResult gameResult) {
		gameResult.boardStates.stream().map(this::convertBoardToString).forEach(board -> {
			BoardEntity entity = getBoardEntity(board).orElse(createBoardEntity(board));
			if (gameResult.winnerIsWhite) entity.increaseValue();
			else entity.decreaseValue();
			boardRepository.save(entity);
		});
	}
	
	private BoardEntity createBoardEntity(String key) {
		BoardEntity entity = new BoardEntity();
		entity.setBoardState(key);
		return entity;
	}
	
	private Optional<BoardEntity> getBoardEntity(String key) {
		return Optional.ofNullable(boardRepository.findOne(key));
	}

	private String convertBoardToString(Integer[][] board) {
		return Stream.of(board).flatMap(Stream::of).map(String::valueOf).collect(Collectors.joining());
	}

}
