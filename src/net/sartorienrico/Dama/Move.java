package net.sartorienrico.Dama;

import java.awt.Color;
import java.util.List;

/**
 * 
 * @author Sartori Enrico
 *
 * Questa classe mi serve per gestire la singola mossa.
 * Salvando gli oggetti in una lista posso salvarmi la storia delle mosse.
 * 
 * All'interno di questa classe verrà anche gestito lo spostamento della pedina nell'interfaccia grafica
 * 
 */

public class Move {
	private Tile origin;
	private Tile destination;
	private Tile eated;
	private boolean isMultiple;

	public Move(Tile origin) throws Exception {
		if (origin.getPiece() == null) 
			throw new MoveException("Impossibile creare una mossa partendo da una tile senza pedina.");
		if ( ! origin.getSelected())
			throw new MoveException("Pedina non valida");
		this.origin = origin;
		this.setOriginUiUpdate();
		if (this.origin.getPiece() != null)
			for (Tile tilemove : this.origin.getPiece().allMoves()) 
				System.out.println(tilemove);
	}
	
	public Move(Tile origin, Tile destination, Tile eated){
		this.origin = origin;
		this.eated = eated;
		this.destination = destination;
	}
	
	// FIELD SET

	public void setDestination(Tile destination) throws Exception {		
		List<Tile> possibleMoves = this.origin.getPiece().allMoves();
		List<Tile> eatMoves = this.origin.getPiece().eatMoves();
		
		// Criteri per validare la destinazione:
		// - Se non posso mangiare qualunque mossa valida è una buona destinazione
		// - Se posso mangiare allora devo mangiare
		
		if (( eatMoves.isEmpty() || eatMoves.contains(destination) ) && possibleMoves.contains(destination)) { 
			this.destination = destination;
			if (eatMoves.contains(this.destination)) {
				this.eated = getEatedPiece(this.origin, this.destination);
				// Se sei una pedina non puoi mangiare un damone
				if (this.eated.getPiece().getIsCrowned() && ! this.origin.getPiece().getIsCrowned())
					throw new MoveException("Se sei una pedina non puoi mangiare un damone");
			}
			this.uiUpdate();
		} else if ( ! eatMoves.isEmpty() && ! eatMoves.contains(destination)) { 
			// Se potevo mangiare e non ho mangiato controllo che le pedine che potevo mangiare non fossero damoni 
			// in tal caso posso muovere anche se non mangio
			for (Tile i : eatMoves) {
				if ( ! getEatedPiece(this.origin, i).getPiece().getIsCrowned() )
					throw new MoveException("Mossa non valida, devi mangiare!");
			}
			this.destination = destination;
			this.uiUpdate();
		} else
			throw new MoveException("Mossa non valida");
	}

	public void setEated(Tile eated) {
		this.eated = eated;
	}
	
	public boolean isMultiple() {
		return isMultiple;
	}

	public static Tile getEatedPiece(Tile origin, Tile destination) {
		int x = (origin.getX() + destination.getX()) / 2;
		int y = (origin.getY() + destination.getY()) / 2;
		return destination.getChessboard().getTileMatrix()[x][y];
	}
	
	public void setOriginUiUpdate() {
		this.origin.getUiTile().setBackground(Color.yellow);
	}
	
	public void uiUpdate(){
		if (this.origin != null && this.destination != null) {
			if (this.eated != null) 
				this.eated.setPiece(null);
			this.origin.getPiece().setCurrentTile(this.destination);
			this.destination.setPiece(this.origin.getPiece());
			this.origin.setPiece(null);
			//this.origin.getUiTile().setBackground();
			if ( this.eated != null && destination.getPiece().canEat()){
				this.isMultiple = true;
				destination.getChessboard().activeTiles();
			} else
				destination.getChessboard().changeTeamColorTurn();
		}
	}
	
	public Tile getOrigin() {
		return this.origin;
	}
}
