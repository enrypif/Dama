package net.sartorienrico.Dama;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class Configuration {
	
	
	// TILES
	public static final Color TILECOLOR_BLACK = new Color(107, 66, 38);
	public static final Color TILECOLOR_WHITE = new Color(235, 199, 158);
	public static final Color TILECOLOR_BLACK_SELECTED = new Color(0, 25, 50);
	public static final Color TILECOLOR_WHITE_SELECTED = new Color(255, 199, 158);
	public static final Color TILECOLOR_BLACK_ABILITED = new Color(0, 25, 50);
	public static final Color TILECOLOR_WHITE_ABILITED = new Color(255, 199, 158);
	public static final Border TILE_SELECTED_BORDER = BorderFactory.createLineBorder(Color.cyan, 2);
	public static final Border TILE_HOVERED_BORDER = BorderFactory.createLineBorder(Color.blue);
	public static final Border TILE_NORMAL_BORDER = BorderFactory.createLineBorder(Color.orange);
	public static BufferedImage TILEIMAGE_BLACK;
	public static BufferedImage TILEIMAGE_WHITE;
	
	//PIECES
	public static final Color PIECECOLOR_BLACK = new Color(192, 192, 192);
	public static final Color PIECECOLOR_WHITE = new Color(255, 255, 255);
	public static Image PIECEIMAGE_BLACK;
	public static Image PIECEIMAGE_WHITE;
	public static Image PIECECROWIMAGE_BLACK;
	public static Image PIECECROWNIMAGE_WHITE;
	
	// ImageIcon GETTERS
	
	public static ImageIcon getTILEICON_WHITE() {
		ImageIcon icon = new ImageIcon(Configuration.getTILEIMAGE_WHITE());
		return icon;
	}
	
	public static ImageIcon getTILEICON_BLACK() {
		ImageIcon icon = new ImageIcon(Configuration.getTILEIMAGE_BLACK());
		return icon;
	}
	
	
	
	public static ImageIcon getPIECEICON_LIGHT(){
		return new ImageIcon(Configuration.getPIECEIMAGE_WHITE());
	}
	
	public static ImageIcon getPIECEICON_DARK(){
		return new ImageIcon(Configuration.getPIECEIMAGE_BLACK());
	}
	
	public static ImageIcon getPIECECROWNICON_LIGHT(){
		return new ImageIcon(Configuration.getPIECECROWNIMAGE_WHITE());
	}
	
	public static ImageIcon getPIECECROWNICON_DARK(){
		return new ImageIcon(Configuration.getPIECECROWNIMAGE_BLACK());
	}
	
	// METODI STATICI PER ISTANZIARE LE IMMAGINI
	
	public static BufferedImage getTILEIMAGE_BLACK() {
		try {
			if (TILEIMAGE_BLACK == null)
				TILEIMAGE_BLACK = ImageIO.read(new File("res/dark.jpg"));
			return TILEIMAGE_BLACK;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage getTILEIMAGE_WHITE() {
		try {
			if (TILEIMAGE_WHITE == null)
				TILEIMAGE_WHITE = ImageIO.read(new File("res/light.jpg"));
			return TILEIMAGE_WHITE;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Image getPIECEIMAGE_WHITE() {
		try {
			if (PIECEIMAGE_WHITE == null)
				PIECEIMAGE_WHITE = ImageIO.read(new File("res/piece_light.png")).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
				//PIECEIMAGE_WHITE = ImageIO.read(new File("res/piece_light.png"));
			return PIECEIMAGE_WHITE;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Image getPIECEIMAGE_BLACK() {
		try {
			if (PIECEIMAGE_BLACK == null)
				PIECEIMAGE_BLACK = ImageIO.read(new File("res/piece_dark.png")).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			return PIECEIMAGE_BLACK;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Image getPIECECROWNIMAGE_WHITE() {
		try {
			if (PIECECROWNIMAGE_WHITE == null)
				PIECECROWNIMAGE_WHITE = ImageIO.read(new File("res/piece_crowned_light.png")).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			return PIECECROWNIMAGE_WHITE;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Image getPIECECROWNIMAGE_BLACK() {
		try {
			if (PIECECROWIMAGE_BLACK == null)
				PIECECROWIMAGE_BLACK = ImageIO.read(new File("res/piece_crowned_dark.png")).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			return PIECECROWIMAGE_BLACK;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
