package test_exe;

import Tournoi.Ia_simple;
import Tournoi.Ia_Alpha_Beta;
import Tournoi.Tournoi;

public class test_ia {

	public static void main(String[] args) {
		Tournoi tournoi = new Tournoi();
		Ia_simple J1 = new Ia_simple();
		Ia_Alpha_Beta J2 = new Ia_Alpha_Beta();
		System.out.println("le gagnant est : "+tournoi.one_match(J1,J2));

	}

}
