package fi.ukkosnetti.chess.logic;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;


public class MoveUtilTest {

	@Test
	public void filtersOutNullMoves() {
		assertTrue(MoveUtil.filterAndTransformMoves(Arrays.asList(null, null)).isEmpty());
	}
}
