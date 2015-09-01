package fi.ukkosnetti.chess.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BoardEntity {

	@Id
	private String boardState;
	
	private Long value = 0l;
	
	public String getBoardState() {
		return boardState;
	}
	
	public void setBoardState(String boardState) {
		this.boardState = boardState;
	}
	
	public Long getValue() {
		return value;
	}
	
	public void setValue(Long value) {
		this.value = value;
	}
	
	public void increaseValue() {
		this.value++;
	}
	
	public void decreaseValue() {
		this.value--;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardState == null) ? 0 : boardState.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardEntity other = (BoardEntity) obj;
		if (boardState == null) {
			if (other.boardState != null)
				return false;
		} else if (!boardState.equals(other.boardState))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BoardEntity [boardState=" + boardState + ", value=" + value + "]";
	}
	
	
	
}
