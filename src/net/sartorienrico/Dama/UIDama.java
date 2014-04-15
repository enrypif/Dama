package net.sartorienrico.Dama;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public static void uiNewGame() {
		Dama.uiDama = new UIDama(800, 798);
		Dama.uiDama.setLayout(new GridLayout(8,8));
		
	}
	
	public void uiWin(int teamColor) {
		JPanel panel = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			  protected void paintComponent(Graphics g) {

			    super.paintComponent(g);
			    try {                
			          Image image = ImageIO.read(new File("res/Dama_init.jpg"));
			          g.drawImage(image , 0, 0, null);
			       } catch (IOException ex) {
			            // handle exception...
			       }
					
			}
		};
		panel.setLayout(new BorderLayout());
		String winnerString = "VINCE IL GIOCATORE " + ((teamColor == 0) ? "BIANCO" : "NERO");
		JLabel winnerLabel = new JLabel(winnerString);
		winnerLabel.setVerticalAlignment(JLabel.CENTER);
		winnerLabel.setHorizontalAlignment(JLabel.CENTER);
		
		
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("res/Chalkduster.ttf")).deriveFont(35f);
			winnerLabel.setFont(font);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		winnerLabel.setVisible(true);
		panel.add(winnerLabel, BorderLayout.CENTER);
		panel.setVisible(true);
		this.setContentPane(panel);
		
		this.setVisible(true);
		this.getContentPane().setVisible(true);
		
	}
}
