package fi.ukkosnetti.chess.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fi.ukkosnetti.chess.model.BoardEntity;

@Repository
public interface BoardEntityRepository extends CrudRepository<BoardEntity, String> {

}
