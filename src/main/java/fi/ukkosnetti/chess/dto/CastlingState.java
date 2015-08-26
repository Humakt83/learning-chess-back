package fi.ukkosnetti.chess.dto;

import java.util.ArrayList;
import java.util.List;

public class CastlingState {
	
	public enum CastlingBlocker {
		WHITE_KING_MOVED, WHITE_LEFT_ROOK_MOVED, WHITE_RIGHT_ROOK_MOVED, 
		BLACK_KING_MOVED, BLACK_LEFT_ROOK_MOVED, BLACK_RIGHT_ROOK_MOVED
	}
	
	public List<CastlingBlocker> blockers = new ArrayList<>();
	
	public List<CastlingBlocker> getBlockers() {
		return blockers;
	}
	
	private void setBlockers(List<CastlingBlocker> blockers) {
		this.blockers = blockers;
	}
	
	public void addBlocker(CastlingBlocker blocker) {
		blockers.add(blocker);
	}
	
	public CastlingState copy() {
		List<CastlingBlocker> blockersCopy = new ArrayList<>();
		blockersCopy.addAll(blockers);
		CastlingState state = new CastlingState();
		state.setBlockers(blockersCopy);
		return state;
	}

	@Override
	public String toString() {
		return "CastlingState [blockers=" + blockers + "]";
	}
	
}
