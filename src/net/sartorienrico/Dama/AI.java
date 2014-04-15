package net.sartorienrico.Dama;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AI {

	ChessBoard chessBoard;
	
	public AI(ChessBoard chessBoard) {
		this.chessBoard = chessBoard;
	}

	public void exec() {
		List<Move> route = this.nextRoute();
		
		if (route.size() != 1) { // Se c'è mangiata
			for (int i = route.size()-1; i >= 0; i--) {
				if (route.get(i) != null){
					aiWait();
					route.get(i).exec();
				}
			}
		} else { // Se ho mangiata singola
			aiWait();
			route.get(0).exec();
		}	
	}
	
	
	public List<Move> nextRoute() {
		List<Move> route = new LinkedList<Move>(); // Creo la lista risultato
		
		//List<Move> bestRoute = AIEngine.bestEatMovesRoute(actionMove.getOrigin().getPiece());
		
		// Di tutte le possibili mosse scelgo la migliore
		List<Move> myPossibleMoves = allPossibleMoves();
		if (myPossibleMoves == null || myPossibleMoves.size() == 0) Dama.win(0);
		for (Move move : myPossibleMoves)
			System.out.println(AIEngine.evaluationFunction(move) + " - " + move);
		Move bestMove = Collections.max(myPossibleMoves);
		
		if (Move.getEatedPiece(bestMove.getOrigin(), bestMove.getDestination()) != null) { // È una mossa mangiata
			route.addAll(AIEngine.bestEatMovesRoute(bestMove.getOrigin().getPiece()));
		} else { // È una mossa semplice
			route.add(bestMove);
		}
		
		return route;		
	}
	
	// Devo costruire un metodo che filtri le mosse
	
	
	
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
	
	// Util method
	
	private void aiWait() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) { 
			System.err.println(e);
		}
	}
	
	// DEBUG METHOD
	
	public void printAllPossibleMoves() {
		List<Move> possibleMoves = allPossibleMoves();
		System.out.println("Possible moves: ");
		for (Move move : possibleMoves)
			System.out.println(move);
	}
}
