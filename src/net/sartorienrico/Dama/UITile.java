package net.sartorienrico.Dama;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class UITile extends JButton implements MouseListener {

	private static final long serialVersionUID = 4744334639853271419L;
	public ActionListener genericEvent;
	private int tileColor;
	
	public UITile(int color) {
		super();
		this.tileColor = color;
		Dama.uiDama.add(this);
		Dama.uiDama.getContentPane().setComponentZOrder(this, 0);
		this.setBackground((color == 0) ? Configuration.TILECOLOR_BLACK : Configuration.TILECOLOR_WHITE);
		this.setOpaque(true);
		Border buttonBorder = BorderFactory.createLineBorder(Color.ORANGE); 
		this.setBorder(buttonBorder);
		this.addMouseListener(this);
	}

	public JButton getCanvasTile() {
		return this;
	}
	
	public void setUIPiece(Piece piece) {
		if (piece == null) this.setIcon(null);
		else if(piece.getTeamColor() == 0) {
			if ( ! piece.getIsCrowned())
				this.setIcon(Configuration.getPIECEICON_LIGHT());
			else
				this.setIcon(Configuration.getPIECECROWNICON_LIGHT());
		} else {
			if (!piece.getIsCrowned())
				this.setIcon(Configuration.getPIECEICON_DARK());
			else
				this.setIcon(Configuration.getPIECECROWNICON_DARK());
		}
	}
	
	public void setUISelected(boolean isSelected) {
		if (isSelected)
			this.setBackground((this.tileColor == 0) ? Configuration.TILECOLOR_BLACK_SELECTED : Configuration.TILECOLOR_WHITE_SELECTED);
		else
			this.setBackground((this.tileColor == 0) ? Configuration.TILECOLOR_BLACK : Configuration.TILECOLOR_WHITE);
	}
	
	public void setUIAbilited(boolean isAbilited) {
		if (isAbilited)
			this.setBackground((this.tileColor == 0) ? Configuration.TILECOLOR_BLACK_SELECTED : Configuration.TILECOLOR_WHITE_SELECTED);
		else
			this.setBackground((this.tileColor == 0) ? Configuration.TILECOLOR_BLACK : Configuration.TILECOLOR_WHITE);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setBorder(Configuration.TILE_HOVERED_BORDER);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setBorder(Configuration.TILE_NORMAL_BORDER);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (genericEvent != null)
			genericEvent.actionPerformed(new ActionEvent(this, 0, ""));
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
