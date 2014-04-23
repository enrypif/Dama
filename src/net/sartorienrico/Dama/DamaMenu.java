package net.sartorienrico.Dama;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DamaMenu extends JFrame {

	private final int height = 300;
	private final int width = 300;
	
	public DamaMenu() {
		this.setSize(width, height);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		JPanel panel = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			  protected void paintComponent(Graphics g) {

			    super.paintComponent(g);
			    try {                
			    		Image image = ImageIO.read(new File("res/DamaMenu.png")).getScaledInstance(300, 280, Image.SCALE_SMOOTH);
			    		g.drawImage(image , 0, 0, null);
			       } catch (IOException ex) {
			            // handle exception...
			       }
					
			}
		};
		
		panel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Dama.newGame();
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		panel.setVisible(true);
		this.setContentPane(panel);
		
		this.setVisible(true);
		this.getContentPane().setVisible(true);
	}
}
