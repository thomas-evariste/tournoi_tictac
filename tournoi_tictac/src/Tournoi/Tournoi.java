package Tournoi;

import java.util.ArrayList;

import Tournoi.Match;

public class Tournoi {
	public int one_match(IA J1, IA J2){
		Match match = new Match();
		int victoir = match.play(J1,J2);
		
		System.out.println("victoir du joueur "+victoir);
		
		return victoir;
	}
	
	public void league(ArrayList<IA> Joueurs){
		Match match = new Match();
		int[] scores = new int[Joueurs.size()];

		
		for(int i=0; i<Joueurs.size();i++){ // matchs aller
			for(int j=i+1; j<Joueurs.size();j++){
				int victoir = match.play(Joueurs.get(i),Joueurs.get(j));
				Joueurs.get(j).reset();
				Joueurs.get(i).reset();
				if(victoir==1){
					scores[i] +=1;
					System.out.println("victoir du joueur "+i+" face au joueur "+j);
				}
				else if(victoir==2){
					scores[j] +=1;
					System.out.println("victoir du joueur "+j+" face au joueur "+i);
				}
			}
		}
		
		for(int i=0; i<Joueurs.size();i++){ // matchs retour
			for(int j=i+1; j<Joueurs.size();j++){
				int victoir = match.play(Joueurs.get(j),Joueurs.get(i));
				Joueurs.get(j).reset();
				Joueurs.get(i).reset();
				if(victoir==1){
					scores[j] +=1;
					System.out.println("victoir du joueur "+j+" face au joueur "+i);
				}
				else if(victoir==2){
					scores[i] +=1;
					System.out.println("victoir du joueur "+i+" face au joueur "+j);
				}
			}
		}
		
		int compt=0;
		//System.out.println(Joueurs.size());
		for(int i=0; i<=(2*Joueurs.size());i++){
			for(int j=0; j<Joueurs.size();j++){
				int vic = (2*Joueurs.size())-i;
				if(scores[j]==vic){
					compt++;
					System.out.println("le joueur "+j+" a fini "+compt+" avec "+vic+" victoire");
				}
			}
		}
		
		
	}

}
