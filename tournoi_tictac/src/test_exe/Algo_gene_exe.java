package test_exe;

import java.io.IOException;
import java.util.ArrayList;

import Tournoi.Algogenetique_AlphaBeta;
import Tournoi.IA;
import Tournoi.Ia_Alpha_Beta;
import Tournoi.Ia_Alpha_Beta_1;
import Tournoi.Ia_Alpha_Beta_2;
import Tournoi.Ia_Alpha_Beta_3;
import Tournoi.Ia_Alpha_Beta_4;
import Tournoi.Ia_Alpha_Beta_5;
import Tournoi.Ia_Alpha_Beta_6;
import Tournoi.Ia_Alpha_Beta_7;
import Tournoi.Ia_Alpha_Beta_8;
import Tournoi.Ia_Alpha_Beta_9;
import Tournoi.Ia_scoring3;
import Tournoi.Ia_simple;

public class Algo_gene_exe {

	public static void main(String[] args) throws IOException {
		ArrayList<IA> Joueurs = new ArrayList<IA>();

		for (int i = 0; i < 2; i++) { 
			Ia_Alpha_Beta J = new Ia_Alpha_Beta();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { 
			Ia_Alpha_Beta_1 J = new Ia_Alpha_Beta_1();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { 
			Ia_Alpha_Beta_2 J = new Ia_Alpha_Beta_2();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { 
			Ia_Alpha_Beta_3 J = new Ia_Alpha_Beta_3();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { 
			Ia_Alpha_Beta_4 J = new Ia_Alpha_Beta_4();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { 
			Ia_Alpha_Beta_5 J = new Ia_Alpha_Beta_5();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { 
			Ia_Alpha_Beta_6 J = new Ia_Alpha_Beta_6();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { 
			Ia_Alpha_Beta_7 J = new Ia_Alpha_Beta_7();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { 
			Ia_Alpha_Beta_8 J = new Ia_Alpha_Beta_8();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { 
			Ia_Alpha_Beta_9 J = new Ia_Alpha_Beta_9();
			Joueurs.add(J);
		}
		for (int i = 0; i < 4; i++) { 
			Ia_simple J = new Ia_simple();
			Joueurs.add(J);
		}
		for (int i = 0; i < 4; i++) { 
			Ia_scoring3 J = new Ia_scoring3();
			Joueurs.add(J);
		}
		// tournoi.league(Joueurs);
		// Ia_scoring J2 = new Ia_scoring(20, 0, 10, 50, 25, -45, -35, -25);
		// tournoi.one_match(J1, J2);
		int[] min = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] max = { 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000 };
		Algogenetique_AlphaBeta alg = new Algogenetique_AlphaBeta(min, max, 5, Joueurs);

		ArrayList<Integer[]> top10 = alg.work(20, 75, 4);
		
	}

}
