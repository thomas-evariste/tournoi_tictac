package Tournoi;

import Tournoi.Match;

public class Tournoi {
	public int one_match(IA J1, IA J2){
		Match match = new Match();
		int victoir = match.play(J1,J2);
		
		System.out.println("victoir du joueur "+victoir);
		
		return victoir;
	}

}
