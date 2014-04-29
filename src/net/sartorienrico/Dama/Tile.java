package net.sartorienrico.Dama;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tile {
	private ChessBoard chessboard;
	// Position
	private int x;
	private int y;
	// Attributes
	private int cellColor;
	private Piece piece;
	private Boolean EMPTY;
	//GUI
	private UITile uiTile; 
	private boolean selected;
	
	// CONSTRUCTOR
	
	public Tile(int cellColor, int x, int y, ChessBoard chessboard) {
		this.chessboard = chessboard;
		this.x = x;
		this.y = y;
		this.setCellColor(cellColor); // 0 : black - 1 : white
		this.piece = null;
		this.EMPTY = true;
		this.addGUI(this.cellColor);
	}
	
	public Tile(int cellColor, int x, int y, ChessBoard chessboard, Piece piece, Boolean withGUI) {
		this.chessboard = chessboard;
		this.x = x;
		this.y = y;
		this.setCellColor(cellColor); // 0 : black - 1 : white
		this.piece = null;
		this.EMPTY = (piece != null) ? false : true;
		if (withGUI == true)
			this.addGUI(this.cellColor);
	}

	// GAME METHOD
	
	
	
	// GETTERS AND SETTERS
	
	public UITile getUiTile(){
		return this.uiTile;
	}
	
	public int getCellColor() {
		return cellColor;
	}

	public void setCellColor(int cellColor) {
		this.cellColor = cellColor;
	}
	
	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		if (piece != null){
			this.EMPTY = false;
			this.piece = piece;
			this.uiTile.setUIPiece(piece);
		} else {
			this.EMPTY = true;
			this.piece = null;
			this.uiTile.setUIPiece(null);
		}
	}
	
	public void setPiece(Piece piece, Boolean withGUI) {
		if (piece != null){
			this.EMPTY = false;
			this.piece = piece;
			if (withGUI == true)
				this.uiTile.setUIPiece(piece);
		} else {
			this.EMPTY = true;
			this.piece = null;
			if (withGUI == true)
				this.uiTile.setUIPiece(null);
		}
	}

	public Boolean getEMPTY() {
		return EMPTY;
	}

	public ChessBoard getChessboard() {
		return chessboard;
	}
	
	public void setChessboard(ChessBoard chessboard) {
		this.chessboard = chessboard;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// GUI METHOD
	/**
	 * Aggiungo all'entità logica "Tile" una "UITile" che è la rispettiva interfaccia grafica
	 * @param cellColor
	 */
	public void addGUI(int cellColor){
		this.uiTile = new UITile(cellColor);
		this.uiTile.genericEvent = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Click on tile
				System.out.println("CLICK X = " + getX() + " - Y = " + getY() );
				Tile thisTile = chessboard.getTileMatrix()[getX()][getY()];	
				
				try {
					if (chessboard.move == null)
						chessboard.move = new Move(thisTile);
					else {
						chessboard.move.setDestination(thisTile, true);
						chessboard.move = null;
					}
				} catch(Exception exc){
					if (chessboard.move != null && chessboard.move.getOrigin() != null)
						chessboard.move.getOrigin().setSelected(true);
					chessboard.move = null; // Se effettuo un'operazione non valida distruggo l'oggetto
					System.out.println(exc);
				}
			}
		};
	}

	@Override
	public String toString() {
		return "Tile [x=" + x + ", y=" + y + ", cellColor=" + cellColor
				+ ", piece=" + piece + ", EMPTY=" + EMPTY + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tile))
			return false;
		Tile other = (Tile) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public void setSelected(boolean isSelected){
		this.selected = isSelected;
		this.uiTile.setUISelected(isSelected);
	}
	
	public void setAbilited(boolean isAbilited) {
		
	}
	
	public boolean getSelected() {
		return this.selected;
	}
}