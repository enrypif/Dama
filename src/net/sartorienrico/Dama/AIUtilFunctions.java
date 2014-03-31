package net.sartorienrico.Dama;

import java.util.LinkedList;
import java.util.List;

/**
 * In questa classe implemento delle funzioni statiche che possono tornare utili all'AI.
 * 
 * @author enrico
 *
 */

public class AIUtilFunctions {
	
	/**
	 * Questo metodo ritorna la migliore strada di mangiate.
	 * Ossia, data una pedina ritorna il maggior numero di mangiate che può effettuare in un turno.
	 * @param Piece
	 * @return List<Move> 
	 */
	public static List<Move> bestEatMovesRoute(Piece piece) {		
		return recursiveBestEatMovesRoute(piece, null);
	}
	
	private static List<Move> recursiveBestEatMovesRoute(Piece piece, Move lastMove) {
		List<Move> myPossibleMoves = piece.possibleMoves();
		List<List<Move>> possibleRoute = new LinkedList<List<Move>>(); 
		for (Move move : myPossibleMoves) {
			if (Move.getEatedPiece(move.getOrigin(), move.getDestination()) != null) { // Se mangio
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
	
}
