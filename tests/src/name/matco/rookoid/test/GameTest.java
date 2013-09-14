package name.matco.rookoid.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.matco.rookoid.game.Game;
import name.matco.rookoid.game.Move;
import name.matco.rookoid.game.Player;
import name.matco.rookoid.game.Square;
import name.matco.rookoid.game.exception.OutOfBoardCoordinateException;
import name.matco.rookoid.game.piece.Piece;
import name.matco.rookoid.game.piece.PieceType;
import name.matco.rookoid.ui.PieceMovement;
import android.test.InstrumentationTestCase;

//TODO modify all asserts: junit asserts parameters are "expected" then "actual"... 
public class GameTest extends InstrumentationTestCase {
	
	public void testGame() {
		final Game game = new Game();
		assertEquals(game.getBoard().length, 64);
		
		try {
			// check some piece positions
			assertEquals(game.getSquareAt(0, 0), game.getBoard()[0]);
			assertEquals(game.getSquareAt(3, 0), game.getBoard()[3]);
			assertEquals(game.getSquareAt(7, 7), game.getBoard()[63]);
		} catch (final OutOfBoardCoordinateException e) {
			fail(e.getLocalizedMessage());
		}
	}
	
	public void testInitialGame() {
		final Game game = new Game();
		
		// check some piece positions
		assertTrue(game.getBoard()[0].getPiece().is(PieceType.ROOK, Player.WHITE));
		assertTrue(game.getBoard()[1].getPiece().is(PieceType.KNIGHT, Player.WHITE));
		assertTrue(game.getBoard()[2].getPiece().is(PieceType.BISHOP, Player.WHITE));
		assertTrue(game.getBoard()[3].getPiece().is(PieceType.QUEEN, Player.WHITE));
		assertTrue(game.getBoard()[4].getPiece().is(PieceType.KING, Player.WHITE));
		assertTrue(game.getBoard()[5].getPiece().is(PieceType.BISHOP, Player.WHITE));
		assertTrue(game.getBoard()[6].getPiece().is(PieceType.KNIGHT, Player.WHITE));
		assertTrue(game.getBoard()[7].getPiece().is(PieceType.ROOK, Player.WHITE));
		for (int i = 8; i < 8 + 8; i++) {
			assertTrue(game.getBoard()[i].getPiece().is(PieceType.PAWN, Player.WHITE));
		}
		for (int i = 48; i < 56; i++) {
			assertTrue(game.getBoard()[i].getPiece().is(PieceType.PAWN, Player.BLACK));
		}
		assertTrue(game.getBoard()[56].getPiece().is(PieceType.ROOK, Player.BLACK));
		assertTrue(game.getBoard()[57].getPiece().is(PieceType.KNIGHT, Player.BLACK));
		assertTrue(game.getBoard()[58].getPiece().is(PieceType.BISHOP, Player.BLACK));
		assertTrue(game.getBoard()[59].getPiece().is(PieceType.QUEEN, Player.BLACK));
		assertTrue(game.getBoard()[60].getPiece().is(PieceType.KING, Player.BLACK));
		assertTrue(game.getBoard()[61].getPiece().is(PieceType.BISHOP, Player.BLACK));
		assertTrue(game.getBoard()[62].getPiece().is(PieceType.KNIGHT, Player.BLACK));
		assertTrue(game.getBoard()[63].getPiece().is(PieceType.ROOK, Player.BLACK));
		
	}
	
	public void testInitialAllowedPositions() {
		final Game game = new Game();
		
		final Piece whiteQueen = game.getBoard()[3].getPiece();
		final Piece whiteKing = game.getBoard()[4].getPiece();
		final Piece whiteQueenPawn = game.getBoard()[11].getPiece();
		
		// check some piece allowed positions
		assertTrue(whiteQueen.getAllowedPositions().isEmpty());
		assertTrue(whiteKing.getAllowedPositions().isEmpty());
		
		final List<Square> whiteQueenPawnAllowedPosition = whiteQueenPawn.getAllowedPositions();
		
		try {
			assertEquals(whiteQueenPawnAllowedPosition.size(), 2);
			assertTrue(whiteQueenPawnAllowedPosition.contains(game.getSquareAt(3, 2)));
			assertTrue(whiteQueenPawnAllowedPosition.contains(game.getSquareAt(3, 3)));
		} catch (final OutOfBoardCoordinateException e) {
			fail(e.getLocalizedMessage());
		}
	}
	
	private void checkBoardConsistency(final Game game) {
		for (final Square s : game.getBoard()) {
			if (s.getPiece() != null && !s.getPiece().getSquare().equals(s)) {
				fail(String.format("Square %s contains piece %s which is linked to square %s", s, s.getPiece(), s.getPiece().getSquare()));
			}
		}
	}
	
	private final Map<Piece, Square> piecePositions = new HashMap<Piece, Square>();
	
	private void saveBoardState(final Game game) {
		piecePositions.clear();
		for (final Piece p : game.getPieces()) {
			piecePositions.put(p, p.getSquare());
		}
	}
	
	public List<PieceMovement> compareAgainsLastSave(final Game game) {
		final List<PieceMovement> movements = new ArrayList<PieceMovement>();
		for (final Piece p : game.getPieces()) {
			if (!p.getSquare().equals(piecePositions.get(p))) {
				movements.add(new PieceMovement(p, piecePositions.get(p), p.getSquare()));
			}
		}
		return movements;
	}
	
	public void testMove() {
		final Game game = new Game();
		final Piece whiteQueenPawn = game.getBoard()[11].getPiece();
		final List<Square> whiteQueenPawnAllowedPosition = whiteQueenPawn.getAllowedPositions();
		
		saveBoardState(game);
		final Move move = game.getMove(whiteQueenPawn, whiteQueenPawnAllowedPosition.get(0));
		game.playMove(move);
		final List<PieceMovement> movements = compareAgainsLastSave(game);
		
		try {
			checkBoardConsistency(game);
			assertEquals(1, movements.size());
			assertEquals(movements.get(0).getPiece(), whiteQueenPawn);
			assertEquals(movements.get(0).getFrom(), game.getSquareAt(3, 1));
			assertEquals(movements.get(0).getTo(), game.getSquareAt(3, 2));
		} catch (final OutOfBoardCoordinateException e) {
			fail(e.getLocalizedMessage());
		}
	}
}
