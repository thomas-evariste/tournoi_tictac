package Tournoi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Algogenetique_AlphaBeta {

	ArrayList<Integer[]> IAs;
	int[] min;
	int[] max;
	Random random;
	ArrayList<IA> Joueurs_test;
	int nb_Joueurs_test;
	int x_nb_ia_30;
	Tournoi tournoi;

	public Algogenetique_AlphaBeta(int[] min, int[] max, int x_nb_ia_30, ArrayList<IA> Joueurs_test) {
		this.min = min;
		this.max = max;
		this.x_nb_ia_30 = x_nb_ia_30;
		this.Joueurs_test = Joueurs_test;
		nb_Joueurs_test = Joueurs_test.size();
		random = new Random();
		IAs = new ArrayList<Integer[]>();
		for (int i = 0; i < 30 * x_nb_ia_30; i++) {
			IAs.add(new_IA_alea());
		}
		tournoi = new Tournoi();
	}

	public Algogenetique_AlphaBeta(int[] min, int[] max, int x_nb_ia_30, ArrayList<IA> Joueurs_test,
			ArrayList<Integer[]> IAs) {
		this.min = min;
		this.max = max;
		this.x_nb_ia_30 = x_nb_ia_30;
		this.Joueurs_test = Joueurs_test;
		nb_Joueurs_test = Joueurs_test.size();
		this.IAs = IAs;
		random = new Random();
		tournoi = new Tournoi();
	}

	public ArrayList<Integer[]> work(int nbIte, int prob_mutation, int nb_max_mutation) throws IOException {

		for (int i = 0; i < nbIte - 1; i++) {

			System.out.println("debut de l'iteratin " + i);

			ArrayList<IA> Joueurs = new ArrayList<IA>();
			for (int j = 0; j < 30 * x_nb_ia_30; j++) {
				Ia_Alpha_Beta_Modulable J = new Ia_Alpha_Beta_Modulable(IAs.get(j)[0], IAs.get(j)[1], IAs.get(j)[2],
						IAs.get(j)[3], IAs.get(j)[4], IAs.get(j)[5], IAs.get(j)[6], IAs.get(j)[7], IAs.get(j)[8]);
				Joueurs.add(J);
			}

			int[][] resu = scoring(Joueurs);

			ArrayList<Integer[]> IAs_int = choix_ia(resu);
			IAs = reproduction(IAs_int, prob_mutation, nb_max_mutation);

		}

		System.out.println("debut de l'iteratin final");

		ArrayList<IA> Joueurs = new ArrayList<IA>();
		for (int j = 0; j < 30 * x_nb_ia_30; j++) {
			Ia_Alpha_Beta_Modulable J = new Ia_Alpha_Beta_Modulable(IAs.get(j)[0], IAs.get(j)[1], IAs.get(j)[2],
					IAs.get(j)[3], IAs.get(j)[4], IAs.get(j)[5], IAs.get(j)[6], IAs.get(j)[7], IAs.get(j)[8]);
			Joueurs.add(J);
		}

		int[][] resu = scoring(Joueurs);

		int compt = 0;

		ArrayList<Integer[]> top10 = new ArrayList<Integer[]>();
		BufferedWriter bw = new BufferedWriter(new FileWriter("resultats5.txt"));
		PrintWriter pWriter = new PrintWriter(bw);
		int nbIA = 30 * x_nb_ia_30;
		pWriter.println("r�sultats pour " + nbIte + " it�ration, " + nbIA + " IA, une probabilit� de " + prob_mutation
				+ "% de mut� et " + nb_max_mutation + " mutaition maximum sur la m�me ia :");

		for (int[] joueur : resu) {
			compt++;
			if (compt == 11) {
				top10.add(IAs.get(joueur[0]));

			}
			System.out.print(" le " + joueur[0] + " a gagn� " + joueur[1] + " partie et a comme caract");
			pWriter.print(" le " + joueur[0] + " a gagn� " + joueur[1] + " partie et a comme caract");
			for (int i = 0; i < 9; i++) {
				System.out.print(" " + IAs.get(joueur[0])[i]);
				pWriter.print(" " + IAs.get(joueur[0])[i]);
			}
			System.out.println(";");
			pWriter.println(";");
		}
		pWriter.close();

		return top10;
	}

	private int[][] scoring(ArrayList<IA> joueurs) {
		int[][] resu_int = new int[joueurs.size()][2];
		int[][] resu = new int[joueurs.size()][2];

		for (int i = 0; i < joueurs.size(); i++) {
			System.err.println("cc " + i);
			resu_int[i][0] = i;
			resu_int[i][1] = 0;
			for (IA joueur_test : Joueurs_test) {
				int resultat = tournoi.one_match(joueurs.get(i), joueur_test);
				joueur_test.reset();
				joueurs.get(i).reset();
				if (resultat == 1) {
					resu_int[i][1]++;
				}
				resultat = tournoi.one_match(joueur_test, joueurs.get(i));
				joueur_test.reset();
				joueurs.get(i).reset();
				if (resultat == 2) {
					resu_int[i][1]++;
				}
			}
		}

		int compt = 0;
		for (int i = 0; i < Joueurs_test.size() * 2; i++) {
			for (int j = 0; j < joueurs.size(); j++) {
				int nb_vic = (Joueurs_test.size() * 2) - i;
				if (resu_int[j][1] == nb_vic) {
					resu[compt][0] = j;
					resu[compt][1] = nb_vic;
					compt++;
				}
			}
		}

		return resu;
	}

	Integer[] new_IA_alea() {
		Integer[] new_IA = new Integer[9];
		for (int i = 0; i < 9; i++) {
			new_IA[i] = random.nextInt(max[i] + 1 - min[i]) + min[i];
		}

		return new_IA;
	}

	private ArrayList<Integer[]> choix_ia(int[][] resu) {
		ArrayList<Integer[]> IAs_int = new ArrayList<Integer[]>();

		for (int i = 0; i < 10 * x_nb_ia_30; i++) {
			IAs_int.add(new_IA_alea());
		}

		for (int i = 10 * x_nb_ia_30; i < 25 * x_nb_ia_30; i++) {
			IAs_int.add(IAs.get(random.nextInt(30)));
		}

		add_top(IAs_int, resu);

		return IAs_int;
	}

	private void add_top(ArrayList<Integer[]> IAs_int, int[][] resu) {

		for (int i = 0; i < 5 * x_nb_ia_30; i++) {
			IAs_int.add(IAs.get(resu[i][0]));
		}

	}

	private ArrayList<Integer[]> reproduction(ArrayList<Integer[]> IAs_int, int prob_mutation, int nb_max_mutation) {
		ArrayList<Integer[]> IAs_ret = new ArrayList<Integer[]>();

		for (int i = 0; i < 2 * x_nb_ia_30; i++) {
			IAs_ret.add(IAs_int.get(i + 25* x_nb_ia_30));
		}

		for (int i = 0; i < 14 * x_nb_ia_30; i++) {
			int ran1 = random.nextInt(30);
			int ran2 = random.nextInt(30);
			int ran3 = random.nextInt(8);
			int ran4 = random.nextInt(100);
			int ran5 = random.nextInt(100);
			Integer[] IA1 = new Integer[9];
			Integer[] IA2 = new Integer[9];
			for (int j = 0; j < 9; j++) {
				if (j < ran3) {
					IA1[j] = IAs.get(ran1)[j];
					IA2[j] = IAs.get(ran2)[j];
				} else {
					IA1[j] = IAs.get(ran2)[j];
					IA2[j] = IAs.get(ran1)[j];
				}
			}
			if (ran4 < prob_mutation) {
				mutation(IA1, nb_max_mutation);
			}
			if (ran5 < prob_mutation) {
				mutation(IA2, nb_max_mutation);
			}
			IAs_ret.add(IA1);
			IAs_ret.add(IA2);
		}

		return IAs_ret;
	}

	private void mutation(Integer[] IA, int nb_max_mutation) {
		for (int i = 0; i < random.nextInt(nb_max_mutation) + 1; i++) {
			int ran = random.nextInt(9);
			IA[ran] = random.nextInt(max[ran] + 1 - min[ran]) + min[ran];
		}
	}

}
