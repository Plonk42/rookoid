package name.matco.rookoid.game;

import name.matco.rookoid.game.exception.OutOfBoardCoordinateException;
import name.matco.rookoid.game.piece.Pawn;

public class EnPassant extends Move {
	
	public EnPassant(final Pawn pawn, final Square to) throws OutOfBoardCoordinateException {
		super(pawn, to);
		this.capturedPiece = Game.getInstance().getSquareAt(to.getCoordinate().x, pawn.getSquare().getCoordinate().y).getPiece();
	}
	
	@Override
	public String getAlgebraic() {
		return String.format("%sx%se.p.", piece.getSquare().getFile(), to.getAlgebraic());
	}
	
	@Override
	public String toString() {
		return String.format("Pawn %s moves %s and captures \"en passant\" %s", piece, movement, capturedPiece);
	}
	
}