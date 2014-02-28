package name.matco.checkmate.game;

import name.matco.checkmate.game.exception.OutOfBoardCoordinateException;
import name.matco.checkmate.game.piece.Piece;

public class Square implements Comparable<Square> {
	
	private final Board board;
	private final int index;
	private Piece piece;
	
	public Square(final Board board, final int index) throws OutOfBoardCoordinateException {
		this.board = board;
		this.index = GameUtils.checkIndex(index);
	}
	
	public int getX() {
		return GameUtils.indexToX(index);
	}
	
	public int getY() {
		return GameUtils.indexToY(index);
	}
	
	public final int getIndex() {
		return index;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public final void setPiece(final Piece piece) {
		this.piece = piece;
	}
	
	public final Piece getPiece() {
		return piece;
	}
	
	public Square apply(final Movement m) throws OutOfBoardCoordinateException {
		return board.getSquareAt(GameUtils.apply(index, m));
	}
	
	public Movement getMovementTo(final Square to) {
		return new Movement(to.getX() - getX(), to.getY() - getY());
	}
	
	@Override
	public String toString() {
		return getX() + "," + getY() + " = " + getAlgebraic();
	}
	
	public String getAlgebraic() {
		return String.format("%s%s", getFile(), getRank());
	}
	
	public char getFile() {
		return (char) ('a' + getX());
	}
	
	public char getRank() {
		return (char) ('1' + getY());
	}
	
	public boolean isEmpty() {
		return getPiece() == null;
	}
	
	public boolean isQueenSide() {
		return getX() <= 3;
	}
	
	public boolean isKingSide() {
		return getX() >= 4;
	}
	
	public boolean isPromotionDestination(final Player player) {
		return getY() == player.getOpponent().getBaseline();
	}
	
	public boolean isCastlingDestination(final Player player) {
		return getY() == player.getBaseline() && (getX() == 2 || getX() == 6);
	}
	
	@Override
	public int compareTo(final Square otherSquare) {
		return getIndex() - otherSquare.getIndex();
	}
}
