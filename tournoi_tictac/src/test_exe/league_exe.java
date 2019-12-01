package test_exe;

import java.util.ArrayList;

import Tournoi.IA;
import Tournoi.Ia_Alpha_Beta;
import Tournoi.Ia_Alpha_Beta_1;
import Tournoi.Ia_Alpha_Beta_10;
import Tournoi.Ia_Alpha_Beta_11;
import Tournoi.Ia_Alpha_Beta_12;
import Tournoi.Ia_Alpha_Beta_2;
import Tournoi.Ia_Alpha_Beta_3;
import Tournoi.Ia_Alpha_Beta_4;
import Tournoi.Ia_Alpha_Beta_5;
import Tournoi.Ia_Alpha_Beta_6;
import Tournoi.Ia_Alpha_Beta_7;
import Tournoi.Ia_Alpha_Beta_8;
import Tournoi.Ia_Alpha_Beta_9;
import Tournoi.Ia_Alpha_Beta_V2;
import Tournoi.Ia_scoring3;
import Tournoi.Ia_simple;
import Tournoi.Tournoi;

public class league_exe {
	public static void main(String[] args) {
		Tournoi tournoi = new Tournoi();
		ArrayList<IA> Joueurs = new ArrayList<IA>();

		for (int i = 0; i < 2; i++) { // 0 1
			Ia_Alpha_Beta J = new Ia_Alpha_Beta();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 2 3
			Ia_Alpha_Beta_1 J = new Ia_Alpha_Beta_1();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 4 5
			Ia_Alpha_Beta_2 J = new Ia_Alpha_Beta_2();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 6 7
			Ia_Alpha_Beta_3 J = new Ia_Alpha_Beta_3();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 8 9
			Ia_Alpha_Beta_4 J = new Ia_Alpha_Beta_4();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 10 11
			Ia_Alpha_Beta_5 J = new Ia_Alpha_Beta_5();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 12 13
			Ia_Alpha_Beta_6 J = new Ia_Alpha_Beta_6();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 14 15
			Ia_Alpha_Beta_7 J = new Ia_Alpha_Beta_7();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 16 17
			Ia_Alpha_Beta_8 J = new Ia_Alpha_Beta_8();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 18 19
			Ia_Alpha_Beta_9 J = new Ia_Alpha_Beta_9();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 20 21
			Ia_Alpha_Beta_10 J = new Ia_Alpha_Beta_10();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 22 23
			Ia_Alpha_Beta_11 J = new Ia_Alpha_Beta_11();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 24 25
			Ia_Alpha_Beta_12 J = new Ia_Alpha_Beta_12();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 26 27
			Ia_simple J = new Ia_simple();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 28 29
			Ia_scoring3 J = new Ia_scoring3();
			Joueurs.add(J);
		}
		for (int i = 0; i < 2; i++) { // 30 31
			Ia_Alpha_Beta_V2 J = new Ia_Alpha_Beta_V2();
			Joueurs.add(J);
		}

		tournoi.league(Joueurs);

	}
}