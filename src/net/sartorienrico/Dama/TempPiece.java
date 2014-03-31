package net.sartorienrico.Dama;

/**
 * Questa classe mi serve per creare una pedina temporanea per poter eseguire al meglio i miei algoritmi.
 * Deve essere perfettamente uguale alla classe Piece.
 * Confrontando la classe di appartenenza di un piece ad un tempPiece vedo che sono diversi, ed in tal caso lo posso cancellare.
 * 
 * @author enrico
 *
 */

public class TempPiece extends Piece {

	public TempPiece(int teamColor, Tile currentTile, int direction) {
		super(teamColor, currentTile, direction);
	}

}
