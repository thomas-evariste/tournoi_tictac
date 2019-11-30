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
		for (int i = 0; i < 3; i++) { // 0 1 2
			Ia_simple J = new Ia_simple();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 3 4
			Ia_scoring1 J = new Ia_scoring1();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 5 6
			Ia_scoring2 J = new Ia_scoring2();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 7 8
			Ia_scoring3 J = new Ia_scoring3();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 9 10
			Ia_scoring_or_serie J = new Ia_scoring_or_serie();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 11 12
			Ia_scoring4 J = new Ia_scoring4();
			Joueurs.add(J);
		}
		// tournoi.league(Joueurs);
		// Ia_scoring J2 = new Ia_scoring(20, 0, 10, 50, 25, -45, -35, -25);
		// tournoi.one_match(J1, J2);
		int[] min = { -500, -500, -500, 0, 0, -1000, -1000, -1000 };
		int[] max = { 500, 500, 500, 1000, 1000, 0, 0, 0 };
		Algogenetique1_v2 alg = new Algogenetique1_v2(min, max, 10, Joueurs);

		ArrayList<Integer[]> top10 = alg.work(250, 75, 4);
		
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
