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

public class Move implements Comparable<Move>{
	private Tile origin;
	private Tile destination;
	private Tile eated;

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

	public void setDestination(Tile destination, boolean autoExec) throws Exception {		
		List<Tile> possibleMoves = this.origin.getPiece().allMoves();
		List<Tile> eatMoves = this.origin.getPiece().eatMoves();
		
		// Criteri per validare la destinazione:
		// - Se non posso mangiare qualunque mossa valida è una buona destinazione
		// - Se posso mangiare allora devo mangiare
		
		if (( eatMoves.isEmpty() || eatMoves.contains(destination) ) && possibleMoves.contains(destination)) { 
			if (eatMoves.contains(destination)) {
				this.eated = getEatedPiece(this.origin, destination);
				// Se sei una pedina non puoi mangiare un damone
				if (this.eated.getPiece().getIsCrowned() && ! this.origin.getPiece().getIsCrowned())
					throw new MoveException("Se sei una pedina non puoi mangiare un damone");
			}
			this.destination = destination;
			if (autoExec == true) this.exec();
		} else if ( ! eatMoves.isEmpty() && ! eatMoves.contains(destination)) { 
			// Se potevo mangiare e non ho mangiato controllo che le pedine che potevo mangiare non fossero damoni 
			// in tal caso posso muovere anche se non mangio
			for (Tile i : eatMoves) {
				if ( ! getEatedPiece(this.origin, i).getPiece().getIsCrowned() )
					throw new MoveException("Mossa non valida, devi mangiare!");
			}
			this.destination = destination;
			if (autoExec == true) this.exec();
		} else
			throw new MoveException("Mossa non valida");
	}

	public void setEated(Tile eated) {
		this.eated = eated;
	}
	
	public boolean isMultiple() {
		// Questo metodo richiede che la mossa sia stata eseguita
		if ( Move.getEatedPiece(this.origin, this.destination) != null && destination.getPiece().canEat()) 
			return true;
		return false;
	}
	
	

	public static Tile getEatedPiece(Tile origin, Tile destination) {
		return getEatedPiece(origin, destination, destination.getChessboard());
	}
	
	public static Tile getEatedPiece(Tile origin, Tile destination, ChessBoard chessBoard) {
		if ( Math.abs(origin.getX() - destination.getX()) != 2) return null; 
		int x = (origin.getX() + destination.getX()) / 2;
		int y = (origin.getY() + destination.getY()) / 2;
		return chessBoard.getTileMatrix()[x][y];
	}
	
	public void setOriginUiUpdate() {
		this.origin.getUiTile().setBackground(Color.yellow);
	}
	
	public void exec(){
		if (this.origin != null && this.destination != null) {
			if (this.eated != null) 
				this.eated.setPiece(null);
			this.origin.getPiece().setCurrentTile(this.destination);
			this.destination.setPiece(this.origin.getPiece());
			this.origin.setPiece(null);
			//this.origin.getUiTile().setBackground();
			if (this.isMultiple())
				destination.getChessboard().activeTiles();
			else
				destination.getChessboard().changeTeamColorTurn();
		}
	}
	
	// Utilizzo questo metodo per simulare le mosse su una ChessBoard che utilizzo solamente a questo scopo
	// In caso di mangiata multipla bisogna passare l'ultima mossa della serie
	public void exec(Tile[][] tileMatrix){
		Tile newOrigin = tileMatrix[this.origin.getX()][this.origin.getY()];
		Tile newDestination = tileMatrix[this.destination.getX()][this.destination.getY()];
		
		Piece originPiece = this.origin.getPiece();
		Piece newOriginPiece = new Piece(originPiece.getTeamColor(), newOrigin, originPiece.getIsCrowned(), originPiece.getDirection()); 
		
		newOrigin.setPiece(newOriginPiece, false);
		
		Tile newEated = null;
		if (this.eated != null)
			 newEated = tileMatrix[this.eated.getX()][this.eated.getY()];
		if (newOrigin != null && newDestination != null) {
			if (newEated != null) 
				newEated.setPiece(null, false);
			newOrigin.getPiece().setCurrentTile(newDestination);
			newDestination.setPiece(newOrigin.getPiece(), false);
			newOrigin.setPiece(null, false);
		}
	}
	
	public Tile getOrigin() {
		return this.origin;
	}
	
	public Tile getDestination() {
		return this.destination;
	}

	@Override
	public String toString() {
		return "Move [origin=" + origin + ", destination=" + destination
				+ ", eated=" + eated + " ]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Move))
			return false;
		Move other = (Move) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (eated == null) {
			if (other.eated != null)
				return false;
		} else if (!eated.equals(other.eated))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		return true;
	}

	@Override
	public int compareTo(Move move) {
		return AIEngine.evaluationFunction(this) - AIEngine.evaluationFunction(move);
	}
	
	
}
