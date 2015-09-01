package fi.ukkosnetti.chess.service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ukkosnetti.chess.model.BoardEntity;
import fi.ukkosnetti.chess.repository.BoardEntityRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardEntityRepository boardRepository;
	
	public BoardEntity getBoardEntity(Integer[][] board) {
		return boardRepository.findOne(convertBoardToString(board));
	}

	private String convertBoardToString(Integer[][] board) {
		return Stream.of(board).flatMap(Stream::of).map(String::valueOf).collect(Collectors.joining());
	}

}
