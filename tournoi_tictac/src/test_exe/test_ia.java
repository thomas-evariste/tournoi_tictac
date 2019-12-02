package test_exe;

import java.util.ArrayList;

import Tournoi.IA;
import Tournoi.Ia_Alpha_Beta_V2;
import Tournoi.Ia_Alpha_Beta_V2_Modulable;
import Tournoi.Tournoi;

public class test_ia {

	public static void main(String[] args) {
		Tournoi tournoi = new Tournoi();
		ArrayList<IA> Joueurs = new ArrayList<IA>();
		Ia_Alpha_Beta_V2_Modulable J = new Ia_Alpha_Beta_V2_Modulable(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
				10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		Ia_Alpha_Beta_V2 J2 = new Ia_Alpha_Beta_V2();

		System.out.println("le vainqueur est : " + tournoi.one_match(J, J2));

	}

}
