package net.sartorienrico.Dama;

public class Dama {
	
	static UIDama uiDama;
	
	public static void main(String[] args) {
		menu();
		//newGame();
		//win(0);
	}
	/**
	 * Creo il menu iniziale
	 */
	public static void menu() {
		
		new DamaMenu();
	}
	
	/**
	 * Creo una nuova finestra di gioco
	 */
	public static void newGame() {
		UIDama.uiNewGame();
		new ChessBoard();
	}
	
	/**
	 * Schermata vittoria
	 * @param teamColor
	 */
	public static void win(int teamColor) {
		uiDama.uiWin(teamColor);
	}

}
