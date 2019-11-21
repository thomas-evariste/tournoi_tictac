package Tournoi;

import java.util.ArrayList;

public class exe {

	public static void main(String[] args) {
		Tournoi tournoi = new Tournoi();
		Ia_simple J1 = new Ia_simple();
		Ia_simple J2 = new Ia_simple();
		tournoi.one_match(J1,J2);
		
		ArrayList<IA> Joueurs = new ArrayList<IA>();
		Joueurs.add(J1);
		Joueurs.add(J2);
		Ia_simple J3 = new Ia_simple();
		Joueurs.add(J3);
		Ia_simple J4 = new Ia_simple();
		Joueurs.add(J4);
		Ia_simple J5 = new Ia_simple();
		Joueurs.add(J5);
		Ia_simple J6 = new Ia_simple();
		Joueurs.add(J6);
		Ia_simple J7 = new Ia_simple();
		Joueurs.add(J7);
		Ia_simple J8 = new Ia_simple();
		Joueurs.add(J8);
		tournoi.league(Joueurs);

	}

}
