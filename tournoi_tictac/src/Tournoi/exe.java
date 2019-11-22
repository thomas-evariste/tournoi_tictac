package Tournoi;

import java.util.ArrayList;

public class exe {

	public static void main(String[] args) {
		// Tournoi tournoi = new Tournoi();
		// Ia_simple J1 = new Ia_simple();
		// Ia_simple J2 = new Ia_simple();
		// tournoi.one_match(J1,J2);
		//
		ArrayList<IA> Joueurs = new ArrayList<IA>();
		for (int i = 0; i < 30; i++) {
			Ia_simple J = new Ia_simple();
			Joueurs.add(J);
		}
		// tournoi.league(Joueurs);
		// Ia_scoring J2 = new Ia_scoring(20, 0, 10, 50, 25, -45, -35, -25);
		// tournoi.one_match(J1, J2);
		int[] min = {-50, -50, -50, 0, 0, -100, -100, -100};
		int[] max = {50, 50, 50, 100, 100, 0, 0, 0};
		Algogenetique1 alg = new Algogenetique1(min, max, Joueurs);

		int[][] top10 = alg.work(1000, 0);
		
		for(int[] j : top10){
			System.out.println();
			System.out.println("les facteur de l'ia sont : ");
			for(int fac:j){
				System.out.print(" "+fac);
			}
		}
	}

}
