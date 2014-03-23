package net.sartorienrico.Dama;

import java.util.LinkedList;
import java.util.List;

public class Piece {
	private int teamColor;
	private Boolean isCrowned; // true = damone
	private Tile currentTile;
	private int direction;
	
	// COSTANTI GLOBALI
	static final int DIRECTION_UP = 0;
	static final int DIRECTION_DOWN = 1;
	static final int DIRECTION_ANY = 2;
	// DIRECTIONAL OFFSETS
	private static int[][] SINGLE_OFFSET_DIRECTION_DOWN 	= { {-1,1}, {1,1} };
	private static int[][] SINGLE_OFFSET_DIRECTION_UP 		= { {-1,-1}, {1,-1} };
	private static int[][] SINGLE_OFFSET_DIRECTION_ANY 		= { {-1,1}, {1,1}, {-1, -1}, {1, -1} };
	private static int[][] DOUBLE_OFFSET_DIRECTION_DOWN 	= { {-2,2}, {2,2} };
	private static int[][] DOUBLE_OFFSET_DIRECTION_UP 		= { {-2,-2}, {2,-2} };
	private static int[][] DOUBLE_OFFSET_DIRECTION_ANY 		= { {-2,2}, {2,2}, {-2, -2}, {2, -2} };


	public Piece(int teamColor, Tile currentTile, int direction) {
		this.teamColor = teamColor;
		this.currentTile = currentTile;
		this.isCrowned = false;
		this.direction = direction;
	}

	// GAME METHOD
	
	public List<Move> possibleMoves() {
		List<Tile> possibleMoves = this.allMoves();
		List<Tile> eatMoves = this.eatMoves();
		
		List<Move> resultList = new LinkedList<Move>();
		
		for (Tile destination : possibleMoves) {
			Tile eated = null;
			Boolean valid = true;
			if (( eatMoves.isEmpty() || eatMoves.contains(destination) ) && possibleMoves.contains(destination)) { 
				if (eatMoves.contains(destination)) {
					eated = Move.getEatedPiece(this.currentTile, destination);
					// Se sei una pedina non puoi mangiare un damone
					if (eated.getPiece().getIsCrowned() && ! this.getIsCrowned())
						// Se sei una pedina non puoi mangiare un damone
						valid = false;
				}
			} else if ( ! eatMoves.isEmpty() && ! eatMoves.contains(destination)) { 
				// Se potevo mangiare e non ho mangiato controllo che le pedine che potevo mangiare non fossero damoni 
				// in tal caso posso muovere anche se non mangio
				for (Tile i : eatMoves) {
					if ( ! Move.getEatedPiece(this.currentTile, i).getPiece().getIsCrowned() )
						// Mossa non valida, devi mangiare!
						valid = false;
				}
			} else
				//Mossa non valida
				valid = false;
			
			if (valid == true) {
				Move move = new Move(this.currentTile, destination, eated);
				resultList.add(move);
			}
		}
		
		return resultList;
	}
	
	public LinkedList<Tile> allMoves() {
		LinkedList<Tile> resultList = new LinkedList<Tile>();
		for (Tile tile : this.simpleMoves()) {
			resultList.add(tile);
		}
		for (Tile tile : this.eatMoves()) {
			resultList.add(tile);
		}
		return resultList;
	}
	
	public LinkedList<Tile> simpleMoves() {
		ChessBoard chessBoard = this.currentTile.getChessboard();
		LinkedList<Tile> resultList = new LinkedList<Tile>();
		
		int[][] singleOffsets = getSingleOffsets();
		
		for  (int[] offset : singleOffsets ) {
			if (ChessBoard.existsTile(this.currentTile.getX() + offset[0], this.currentTile.getY() + offset[1]))					
				if (chessBoard.getTileMatrix()[this.currentTile.getX() + offset[0]][this.currentTile.getY() + offset[1]].getEMPTY())
					resultList.add(chessBoard.getTileMatrix()[this.currentTile.getX() + offset[0]][this.currentTile.getY() + offset[1]]);
		}
		
		return resultList;
	}
	
	public LinkedList<Tile> eatMoves() {
		ChessBoard chessBoard = this.currentTile.getChessboard();
		LinkedList<Tile> resultList = new LinkedList<Tile>();
		
		int[][] singleOffsets = getSingleOffsets();
		int[][] doubleOffsets = getDoubleOffsets();
		
		for (int i = 0; i < singleOffsets.length; i++) {
			int[] singleOffset = singleOffsets[i];
			int[] doubleOffset = doubleOffsets[i];
			
			if (ChessBoard.existsTile(this.currentTile.getX() + singleOffset[0], this.currentTile.getY() + singleOffset[1]) 
					&& ChessBoard.existsTile(this.currentTile.getX() + doubleOffset[0], this.currentTile.getY() + doubleOffset[1]))
				if ( ! chessBoard.getTileMatrix()[this.currentTile.getX() + singleOffset[0]][this.currentTile.getY() + singleOffset[1]].getEMPTY() 
						&& chessBoard.getTileMatrix()[this.currentTile.getX() + doubleOffset[0]][this.currentTile.getY() + doubleOffset[1]].getEMPTY() 
						&& chessBoard.getTileMatrix()[this.currentTile.getX() + singleOffset[0]][this.currentTile.getY() + singleOffset[1]].getPiece().getTeamColor() != this.teamColor )
					resultList.add(chessBoard.getTileMatrix()[this.currentTile.getX() + doubleOffset[0]][this.currentTile.getY() + doubleOffset[1]]);
		}
		
		return resultList;
	}
	
	public Boolean canEat() {
		// Se sono un damone posso mangiare chiunque
		if ( this.getIsCrowned() && ! this.eatMoves().isEmpty() ) return true;
		// Se sono una pedina non posso mangiare i damoni
		for (Tile i : this.eatMoves())
			if ( ! Move.getEatedPiece(this.currentTile, i).getPiece().getIsCrowned() )
				return true;
		return false;
	}
	
	// ------------------------------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------------------------------
	// GETTERS AND SETTERS
	
	private int[][] getSingleOffsets() {
		switch(this.direction) {
			case DIRECTION_DOWN:
				return SINGLE_OFFSET_DIRECTION_DOWN;
			case DIRECTION_UP:
				return SINGLE_OFFSET_DIRECTION_UP;
			case DIRECTION_ANY:
				return SINGLE_OFFSET_DIRECTION_ANY;
			default:
				return null;
		}
	}
	
	private int[][] getDoubleOffsets() {
		switch(this.direction) {
			case DIRECTION_DOWN:
				return DOUBLE_OFFSET_DIRECTION_DOWN;
			case DIRECTION_UP:
				return DOUBLE_OFFSET_DIRECTION_UP;
			case DIRECTION_ANY:
				return DOUBLE_OFFSET_DIRECTION_ANY;
			default:
				return null;
		}
	}
	
	public int getTeamColor() {
		return teamColor;
	}

	public void setTeamColor(int teamColor) {
		this.teamColor = teamColor;
	}

	public Tile getCurrentTile() {
		return currentTile;
	}

	public void setCurrentTile(Tile currentTile) {
		this.currentTile = currentTile;
		if (this.direction == DIRECTION_UP && currentTile.getY() == 0) {
			this.isCrowned = true;
			this.direction = DIRECTION_ANY;
		}
		if (this.direction == DIRECTION_DOWN && currentTile.getY() == 7) {
			this.isCrowned = true;
			this.direction = DIRECTION_ANY;
		}
	}

	public Boolean getIsCrowned() {
		return isCrowned;
	}

	public void setIsCrowned(Boolean isCrowned) {
		this.isCrowned = isCrowned;
		if (isCrowned) this.direction = DIRECTION_ANY;
	}
	
}
