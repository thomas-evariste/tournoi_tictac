package Tournoi;

import java.io.IOException;
import java.util.ArrayList;

public class exe {

	public static void main(String[] args) throws IOException {
		// Tournoi tournoi = new Tournoi();
		// Ia_simple J1 = new Ia_simple();
		// Ia_simple J2 = new Ia_simple();
		// tournoi.one_match(J1,J2);
		//
		ArrayList<IA> Joueurs = new ArrayList<IA>();
		
		for (int i = 0; i < 4; i++) { 
			Ia_Alpha_Beta J = new Ia_Alpha_Beta();
			Joueurs.add(J);
		}
		// tournoi.league(Joueurs);
		// Ia_scoring J2 = new Ia_scoring(20, 0, 10, 50, 25, -45, -35, -25);
		// tournoi.one_match(J1, J2);
		int[] min = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] max = { 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000 };
		Algogenetique_AlphaBeta alg = new Algogenetique_AlphaBeta(min, max, 1, Joueurs);

		ArrayList<Integer[]> top10 = alg.work(5, 75, 4);
		
//
//		for (Integer[] j : top10) {
//			System.out.println();
//			System.out.println("les facteur de l'ia sont : ");
//			for (int fac : j) {
//				System.out.print(" " + fac);
//			}
//		}
	}
}
