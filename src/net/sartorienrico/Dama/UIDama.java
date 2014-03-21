package net.sartorienrico.Dama;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UIDama extends JFrame {
	
	private static final long serialVersionUID = 7625359963486005937L;
	
	// Attributes
	private int height;
	private int width;
	
	/**
	 * Constructor of chessboard interface
	 *  
	 * @param width
	 * @param height
	 */
	public UIDama(int width, int height) {
		this.height = height;
		this.width = width;
		this.setResizable(false);
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(8,8));
		this.setVisible(true);
		
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void uiWin(int teamColor) {
		this.setLayout(new GridLayout(1,1));
		JPanel panel = new JPanel();
		String winnerString = "Il giocatore " + ((teamColor == 0) ? "bianco" : "nero") + " ha vinto";
		JLabel winnerLabel = new JLabel(winnerString);
		winnerLabel.setVisible(true);
		panel.add(winnerLabel);
		panel.setVisible(true);
		this.setContentPane(panel);
		
		this.setVisible(true);
		this.getContentPane().setVisible(true);
		
	}
}
