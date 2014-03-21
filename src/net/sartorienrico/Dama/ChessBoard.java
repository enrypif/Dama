package net.sartorienrico.Dama;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
	private Tile tileMatrix[][]; 	// Matrice scacchiera ogni tile rappresenta un riquadro
	public Move move;
	private int teamColorTurn;
	
	/**
	 * ChessBoard constructor
	 * Metodo per l'inizializzazione di una nuova ChessBoard
	 * @return	ChessBoard	Returns a new instance of ChessBoard
	 */
	public ChessBoard() { 
		super();
		this.tileMatrix = new Tile[8][8];
		for	(int j = 7; j >= 0; j--){ // Scorro ed inizializzo gli elementi della scacchiera
			for (int i = 7; i >= 0; i--){
				if ((i+j)%2 == 0){
					this.tileMatrix[i][j] = new Tile(0, i, j, this); // Black cells
					if (j >= 0 && j <= 2) {
						Piece piece = new Piece(0, this.tileMatrix[i][j], Piece.DIRECTION_DOWN);
						this.tileMatrix[i][j].setPiece(piece); // Inserisco nell'oggeto tile l'oggetto piece (pedina)
					} else if (j >= 5 && j <= 7) {
						Piece piece = new Piece(1, this.tileMatrix[i][j], Piece.DIRECTION_UP);
						this.tileMatrix[i][j].setPiece(piece); // Il player avrà le pedine gialle
					}
				} else {
					this.tileMatrix[i][j] = new Tile(1, i, j, this); // White cells
				}
			}
		}
		this.teamColorTurn = 0;
		this.changeTeamColorTurn();
		Dama.uiDama.setVisible(true);
	}
	
	
	
	// Utils method
	
	public static Boolean existsTile(int i, int j) {
		if (i >= 0 && i <= 7 && j >= 0 && j <= 7) // Sono all'interno della scacchiera
			return true;
		else 
			return false;
	}
	
	public int changeTeamColorTurn(){
		this.teamColorTurn = 1 - this.teamColorTurn;
		// Cosa succede quando cambia il turno?
		// Si illuminano le pedine che possono essere mosse.
		activeTiles();
		return this.teamColorTurn;
	}
	
	public void activeTiles() {
		List<Tile>activableTiles = getActivableTiles();
		if (activableTiles != null)
			for (Tile[] tiles : this.tileMatrix) {
				for (Tile tile : tiles) {
					if (activableTiles.contains(tile))
						tile.setSelected(true);
					else
						tile.setSelected(false);
				}
			}
		else { // Il giocatore di questo turno ha perso
			// System.out.println("Il giocatore " + ((this.teamColorTurn == 0) ? "bianco" : "nero") + " ha perso");
			Dama.uiDama.uiWin( 1 - teamColorTurn );
		}
	}
	
	public List<Tile> getActivableTiles() {
		List<Tile> activableTiles = new ArrayList<Tile>();
		List<Tile> activableEatTiles = new ArrayList<Tile>();
		for (Tile[] tiles : this.tileMatrix){
			for (Tile tile : tiles){
				// Filtro le pedine che possono essere mosse
				Piece piece = ( ! tile.getEMPTY() ) ? tile.getPiece() : null;
				if ( piece != null
						&& tile.getPiece().getTeamColor() == this.teamColorTurn
						&& ! piece.possibleMoves().isEmpty()
						){
					if ( ! piece.canEat())
						activableTiles.add(tile);
					else 
						activableEatTiles.add(tile);
				}
			}
		}
		// Se qualcuno può mangiare tolgo tutte le altre
		
		if ( ! activableEatTiles.isEmpty())
			return activableEatTiles;
		else if ( ! activableTiles.isEmpty())
			return activableTiles;
		else // Se ritorno null significa che il giocatore non ha più mosse a disposizione, quindi ha perso.
			return null;  
	}
	
	// Getters and Setters
	
	public Tile[][] getTileMatrix() {
		return tileMatrix;
	}

	public void setTileMatrix(Tile[][] tileMatrix) {
		this.tileMatrix = tileMatrix;
	}
	
	public int getTeamColorTurn() {
		return teamColorTurn;
	}

	@Override
	public String toString(){
		String ret = "";
		for (int j = 0; j < this.tileMatrix.length; j++){
			for (int i = 0; i < this.tileMatrix.length; i++){
				if (this.tileMatrix[j][i].getEMPTY() == false){
					if (this.tileMatrix[j][i].getPiece().getTeamColor() == 0)
						ret += " 0 ";
					else
						ret += " 1 ";
				} else {
					ret += " _ ";
				}
			}
			ret += "\n";
		}
		return ret;
	}
}