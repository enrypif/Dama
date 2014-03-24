package net.sartorienrico.Dama;

import java.util.LinkedList;
import java.util.List;

public class AI {

	ChessBoard chessBoard;
	
	public AI(ChessBoard chessBoard) {
		this.chessBoard = chessBoard;
	}

	public void exec() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.err.println(e);
		}
		Move actionMove = this.randomMove();
		actionMove.exec();
	}
	
	public Move randomMove() {
		List<Move> myPossibleMoves = allPossibleMoves();
		int range = myPossibleMoves.size();
		int index = (int)(Math.random() * (range));
		return myPossibleMoves.get(index);		
	}
	
	public List<Move> allPossibleMoves() {
		List<Move> allPossibleMoves = new LinkedList<Move>();
		List<Tile> activableTiles = this.chessBoard.getActivableTiles();
		for (Tile activableTile : activableTiles) {
			List<Move> piecePossibleMoves = activableTile.getPiece().possibleMoves();
			for (Move move : piecePossibleMoves)
				allPossibleMoves.add(move);
		}
		return allPossibleMoves;
	}

	// DEBUG METHOD
	
	public void printAllPossibleMoves(){
		List<Move> possibleMoves = allPossibleMoves();
		System.out.println("Possible moves: ");
		for (Move move : possibleMoves)
			System.out.println(move);
	}
}
