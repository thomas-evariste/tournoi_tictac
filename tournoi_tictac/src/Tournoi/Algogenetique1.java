package Tournoi;

import java.util.ArrayList;
import java.util.Random;

public class Algogenetique1 {
	// gestion de 30 ia (8 int)
	//
	// séléction init:
	// -5 aléatoire
	// -15 hasard dans la génération précédente
	// -10 meilleur de la génération précédente
	//
	// selection final:
	// -4 meilleur
	// -26 aléa dans séléction init qui se reproduise
	//
	// reproduction:
	// -croisement
	// -possibilité de mutation

	int[][] IAs = new int[30][8];
	int[] min = new int[8];
	int[] max = new int[8];
	Random random;
	ArrayList<IA> Joueurs_test;
	int nb_Joueurs_test;

	Algogenetique1(int[] min, int[] max, ArrayList<IA> Joueurs_test) {
		this.min = min;
		this.max = max;
		this.Joueurs_test = Joueurs_test;
		nb_Joueurs_test = Joueurs_test.size();
		random = new Random();
		for (int i = 0; i < 30; i++) {
			IAs[i] = new_IA_alea();
		}
	}

	Algogenetique1(int[] min, int[] max, ArrayList<IA> Joueurs_test, int[][] IAs) {
		this.min = min;
		this.max = max;
		this.Joueurs_test = Joueurs_test;
		nb_Joueurs_test = Joueurs_test.size();
		this.IAs = IAs;
		random = new Random();
	}

	int[][] work(int nbIte, double prob_mutation) {

		Tournoi tournoi = new Tournoi();

		for (int i = 0; i < nbIte - 1; i++) {
			
			System.out.println("debut de l'iteratin "+i);
			
			ArrayList<IA> Joueurs = new ArrayList<IA>(Joueurs_test);
			for (int j = 0; j < 30; j++) {
				Ia_scoring J = new Ia_scoring(IAs[j][0], IAs[j][1], IAs[j][2], IAs[j][3], IAs[j][4], IAs[j][5],
						IAs[j][6], IAs[j][7]);
				Joueurs.add(J);
			}

			int[][] resu = tournoi.league(Joueurs);

			int[][] IAs_int = choix_ia(resu);
			IAs = reproduction(IAs_int);

		}

		ArrayList<IA> Joueurs = new ArrayList<IA>(Joueurs_test);
		for (int j = 0; j < 30; j++) {
			Ia_scoring J = new Ia_scoring(IAs[j][0], IAs[j][1], IAs[j][2], IAs[j][3], IAs[j][4], IAs[j][5], IAs[j][6],
					IAs[j][7]);
			Joueurs.add(J);
		}
		int[][] resu = tournoi.league(Joueurs);

		int compt = 0;
		int[][] top10 = new int[10][8];

		for (int[] joueur : resu) {
			if (joueur[0] > nb_Joueurs_test - 1) {
				top10[compt] = IAs[joueur[0] - (nb_Joueurs_test)];
				compt++;
				if (compt == 10) {
					break;
				}
			}
		}

		return top10;
	}

	int[] new_IA_alea() {
		int[] new_IA = new int[8];
		for (int i = 0; i < 8; i++) {
			new_IA[i] = random.nextInt(max[i] + 1 - min[i]) + min[i];
		}

		return new_IA;
	}

	private int[][] choix_ia(int[][] resu) {
		int[][] IAs_int = new int[30][8];

		for (int i = 0; i < 5; i++) {
			IAs_int[i] = new_IA_alea();
		}

		for (int i = 5; i < 20; i++) {
			IAs_int[i] = IAs[random.nextInt(30)];
		}

		add_top10(IAs_int, resu);

		return IAs_int;
	}

	private void add_top10(int[][] iAs_int, int[][] resu) {
		int compt = 0;

		for (int[] joueur : resu) {
			if (joueur[0] > nb_Joueurs_test - 1) {
				iAs_int[20 + compt] = IAs[joueur[0] - (nb_Joueurs_test)];
				compt++;
				if (compt == 10) {
					break;
				}
			}
		}

	}

	private int[][] reproduction(int[][] iAs_int) {

		return iAs_int;
	}

}
