package Tournoi;

import java.util.ArrayList;
import java.util.Stack;

public class Ia_scoring4 extends IA {

	public Table_scoring table;
	public Scoring scoring;

	Ia_scoring4() {

		scoring = new Scoring(413, -169, 355, 125, 912, -647, -974, -565);

		ArrayList<Part_scoring> parts = new ArrayList<Part_scoring>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Part_scoring part = new Part_scoring(i, j);
				parts.add(part);
			}
		}

		table = new Table_scoring(parts);
	}

	public void reset() {

		ArrayList<Part_scoring> parts = new ArrayList<Part_scoring>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Part_scoring part = new Part_scoring(i, j);
				parts.add(part);
			}
		}

		table = new Table_scoring(parts);
	}

	public int[] play(Stack<Integer> entre) {

		int opponentRow = entre.pop();
		int opponentCol = entre.pop();
		table.getPart(opponentRow / 3, opponentCol / 3).update_pos();

		table.getPart(opponentRow / 3, opponentCol / 3).getCase(opponentRow, opponentCol).setpos(-1);

		int validActionCount = entre.pop();

		int x = -1;
		int y = -1;
		int score = -100000;
		int row = -1;
		int col = -1;

		for (int i = 0; i < validActionCount; i++) {
			row = entre.pop();
			col = entre.pop();
			int score_temp = scoring.calc_score(row, col, table);
			//System.err.println("la case: " + row + " " + col + " a un score de " + score_temp);
			if (score_temp > score) {
				score = score_temp;
				x = row;
				y = col;
			}
		}

		table.getPart(x / 3, y / 3).getCase(x, y).setpos(1);
		table.getPart(x / 3, y / 3).update_pos();
		int[] pos_final = { x, y };
		//System.err.println("il a joué :" + opponentRow + " " + opponentCol);
		//System.err.println("je joue :" + x + " " + y);
		return pos_final;
		// System.out.println(x+" "+y);
	}
}
