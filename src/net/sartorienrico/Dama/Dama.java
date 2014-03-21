package net.sartorienrico.Dama;

public class Dama {
	
	static UIDama uiDama;
	
	public static void main(String[] args) {
		uiDama = new UIDama(800, 798);
		
		new ChessBoard();
		
	}

}
