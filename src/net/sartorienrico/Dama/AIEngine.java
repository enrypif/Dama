package net.sartorienrico.Dama;

import java.util.LinkedList;
import java.util.List;

/**
 * In questa classe implemento delle funzioni statiche che possono tornare utili all'AI.
 * 
 * @author enrico
 *
 */

public class AIEngine {
	
	// PUNTEGGI
	
	private static int EAT_WEIGHT = 100;
	private static int DEATH_WEIGHT = -50;
	private static int ADVANCEMENT_WEIGHT = 5; 
	private static int CENTER_WEIGHT = 2;
	private static int POSITION_CROWNED = 2;
	
	// Public methods
	
	/**
	 * Questo metodo ritorna la migliore strada di mangiate.
	 * Ossia, data una pedina ritorna il maggior numero di mangiate che può effettuare in un turno.
	 * @param Piece
	 * @return List<Move> 
	 */
	
	public static List<Move> bestEatMovesRoute(Piece piece) {		
		List<Move> result = recursiveBestEatMovesRoute(piece, null);
		//for (Move move : result) System.out.println(move);
		return result;
	}
	
	
	/**
	 * Functione di valutazione mossa
	 * Questa funzione richima una serie di filtri che le permettono di assegnare un punteggio alla mossa
	 * @param move
	 * @return int score
	 */
	
	public static int evaluationFunction(Move move) {
		int score = 0;
		score += eatsScore(move); // Punti per le mangiate
		score += centerScore(move); // Punti per la centratura
		
		if ( ! move.getOrigin().getPiece().getIsCrowned()) {
			// La pedina presa in considerazione non è damone
			score += deathScore(move); // Punti per la mangiata subita
			score += advancementScore(move); // Punti per l'avanzamento
		} else {
			score += deathScore(move) * 3;
			score += positionScoreForCrowned(move);
		}
		
		return score;
	}
	
	// Private methods
	
	private static int deathScore(Move move) {
		int numberOfPossibleDeath = 0;
		Tile[][] origTileMatrix = move.getDestination().getChessboard().getTileMatrix();
		Tile[][] destTileMatrix = cloneTileMatrix(origTileMatrix);
		move.exec(destTileMatrix);
		ChessBoard simulation = new ChessBoard(destTileMatrix, move.getOrigin().getChessboard().getTeamColorTurn());
		updateTileChessBoardPointer(simulation);
		List<Tile> activableTiles = simulation.getActivableTiles(1 - move.getOrigin().getChessboard().getTeamColorTurn());
		// Analizzo tutte le possibili mosse e controllo quante mosse mangiate potrebbe effettuare il giocatore nemico ed assegno il punteggio di conseguenza
		if (activableTiles == null) return 0;
		for (Tile tile : activableTiles) {
			List<Move> moves = tile.getPiece().possibleMoves();
			for (Move thisMove : moves) {
				int eatLength = bestEatMovesRouteLength(thisMove.getOrigin().getPiece());
				numberOfPossibleDeath += eatLength;
			}
		}
		return numberOfPossibleDeath * DEATH_WEIGHT;
	}
		
	private static int eatsScore(Move move) {
		if (Move.getEatedPiece(move.getOrigin(), move.getDestination()) != null) 
			if ( bestEatMoveForPiece(move) ){
				int routeLength = bestEatMovesRouteLength(move.getOrigin().getPiece());
				return ( EAT_WEIGHT * routeLength );
			}
		return 0;
	}
	
	private static int advancementScore(Move move) {
		// Muovo quelle più indietro
		// return ADVANCEMENT_WEIGHT * (7 - move.getDestination().getY());
		
		// Muovo quelle più avanti
		return ADVANCEMENT_WEIGHT * (move.getDestination().getY()); 
	}
	
	private static int centerScore(Move move) {
		// Cerco di mantenere il gioco al centro della scacchiera
		return CENTER_WEIGHT * (int)Math.round(3.5 - Math.abs(3.5 - move.getDestination().getX())); 
	}
	
	private static int positionScoreForCrowned(Move move) {
		// La posizione preferenziale del damone è al centro
		Tile destinationTile = move.getDestination();
		int x = destinationTile.getX();
		int y = destinationTile.getY();
		int score = 0;
		score = (14 - (int) Math.round(Math.abs(3.5 - x) + Math.abs(3.5 - y))) * POSITION_CROWNED;
		return score;
	}
	
	/** 
	 * Metodo che data una pedina dice se la seguente mossa è quella con più mangiate disponibili
	 * @param Move
	 * @return Boolean
	 */
	
	private static Boolean bestEatMoveForPiece(Move move) {
		List<Move> route = bestEatMovesRoute(move.getOrigin().getPiece());
		// La prima mossa da effettuare è quella in cima alla pila
		Move bestMove = route.get(route.size()-2);
		Boolean res = move.equals(bestMove);
		return res;
	}
	
	
	/**
	 * Metodo che data una pedina restituisce il numero massimo di pedine che può mangiare
	 * @param piece
	 * @return numero di mangiate
	 */
	
	private static int bestEatMovesRouteLength(Piece piece) {
		return bestEatMovesRoute(piece).size() - 1;
	}
	
	
	private static List<Move> recursiveBestEatMovesRoute(Piece piece, Move lastMove) {
		List<Move> myPossibleMoves = piece.possibleMoves();
		List<List<Move>> possibleRoute = new LinkedList<List<Move>>(); 
		for (Move move : myPossibleMoves) {
			if (lastMove == null || (lastMove != null && ! move.getDestination().equals(lastMove.getOrigin())))
				if ( Move.getEatedPiece(move.getOrigin(), move.getDestination()) != null ) { // Se mangio
					Piece tempPiece = new Piece(piece.getTeamColor(), move.getDestination(), piece.getDirection());
					possibleRoute.add( recursiveBestEatMovesRoute(tempPiece, move) );
				}
		}
		
		// Seleziono il percorso migliore tra quelli disponibili
		List<Move> bestRoute = null;
		for (List<Move> route : possibleRoute) {
			if (bestRoute == null || bestRoute.size() < route.size())
				bestRoute = route; 
		}
		
		// Ritorno il percorso migliore
		if (bestRoute != null) {
			bestRoute.add(lastMove);
			return bestRoute;
		} else { // Caso base

			// È l'ultima mangiata
			List<Move> retMoves = new LinkedList<Move>();
			retMoves.add(lastMove);
			return retMoves;
		}
	}
	
	private static Tile[][] cloneTileMatrix(Tile[][] origTileMatrix) {
		Tile[][] destTileMatrix = new Tile[8][8];
		for	(int j = 7; j >= 0; j--){ // Scorro ed inizializzo gli elementi della scacchiera
			for (int i = 7; i >= 0; i--){
				Tile origTile = origTileMatrix[i][j];
				destTileMatrix[i][j] = new Tile(origTile.getCellColor(), origTile.getX(), origTile.getY(), origTile.getChessboard(), null,false);
				if (origTile.getPiece() != null) {
					Piece piece = new Piece(origTile.getPiece().getTeamColor(), destTileMatrix[i][j], origTile.getPiece().getIsCrowned(), origTile.getPiece().getDirection());
					destTileMatrix[i][j].setPiece(piece, false);
				}
			}
		}
		return destTileMatrix;
	}
	
	private static ChessBoard updateTileChessBoardPointer(ChessBoard referChessBoard) {
		Tile[][] tileMatrix = referChessBoard.getTileMatrix();
		for (Tile[] tiles : tileMatrix)
			for (Tile tile : tiles)
				tile.setChessboard(referChessBoard);
		return referChessBoard;
	}
	
}
