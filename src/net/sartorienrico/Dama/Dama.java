package net.sartorienrico.Dama;

public class Dama {
	
	static UIDama uiDama;
	
	public static void main(String[] args) {
		newGame();
		//win(0);
	}
	
	public static void newGame() {
		UIDama.uiNewGame();
		new ChessBoard();
	}
	
	public static void win(int teamColor) {
		uiDama.uiWin(teamColor);
	}

}
