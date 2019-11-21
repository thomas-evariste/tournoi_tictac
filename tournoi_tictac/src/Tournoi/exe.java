package Tournoi;

import java.util.ArrayList;

public class exe {

	public static void main(String[] args) {
		Tournoi tournoi = new Tournoi();
		Ia_simple J1 = new Ia_simple();
		Ia_simple J2 = new Ia_simple();
		tournoi.one_match(J1,J2);
		
		ArrayList<IA> Joueurs = new ArrayList<IA>();
		for(int i =0; i<20;i++){
			Ia_simple J = new Ia_simple();
			Joueurs.add(J);
		}
		tournoi.league(Joueurs);

	}

}
