package Tournoi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Ia_scoring extends IA {

	public Table_scoring table;
	public Scoring scoring;

	Ia_scoring(int coin, int cote, int centre, int finir, int emp_finir, int env_finit, int env_peut_finir,
			int env_emp_finir) {

		scoring = new Scoring(coin, cote, centre, finir, emp_finir, env_finit, env_peut_finir, env_emp_finir);

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
			System.err.println("la case: "+row+" "+col+" a un score de "+score_temp);
			if (score_temp > score) {
				score = score_temp;
				x = row;
				y = col;
			}
		}

		table.getPart(x / 3, y / 3).getCase(x, y).setpos(1);
		table.getPart(x / 3, y / 3).update_pos();
		int[] pos_final = { x, y };
		System.err.println("il a joué :"+opponentRow+" "+opponentCol);
		System.err.println("je joue :"+x+" "+y);
		return pos_final;
		// System.out.println(x+" "+y);
	}
}

class Scoring {
	Integer[][][] allignes = { { { 0, 0 }, { 0, 1 }, { 0, 2 } }, { { 1, 0 }, { 1, 1 }, { 1, 2 } },
			{ { 2, 0 }, { 2, 1 }, { 2, 2 } }, { { 0, 0 }, { 1, 0 }, { 2, 0 } }, { { 0, 1 }, { 1, 1 }, { 2, 1 } },
			{ { 0, 2 }, { 1, 2 }, { 2, 2 } }, { { 0, 0 }, { 1, 1 }, { 2, 2 } }, { { 0, 2 }, { 1, 1 }, { 2, 0 } } };
	int coin;
	int cote;
	int centre;
	int finir;
	int emp_finir;
	int env_finit;
	int env_peut_finir;
	int env_emp_finir;

	Scoring(int coin, int cote, int centre, int finir, int emp_finir, int env_finit, int env_peut_finir,
			int env_emp_finir) {
		this.coin = coin;
		this.cote = cote;
		this.centre = centre;
		this.finir = finir;
		this.emp_finir = emp_finir;
		this.env_finit = env_finit;
		this.env_peut_finir = env_peut_finir;
		this.env_emp_finir = env_emp_finir;
	}

	int calc_score(int row, int col, Table_scoring table) {
		int score = 0;

		if (verif_cote(row, col, table)) {
			score += cote;
		}

		else if (verif_centre(row, col, table)) {
			score += centre;
		}

		else {
			score += coin;
		}

		if (verif_finir(row, col, table)) {
			score += finir;
		}

		if (verif_emp_finir(row, col, table)) {
			score += emp_finir;
		}

		if (verif_env_finit(row, col, table)) {
			score += env_finit;
		}

		if (verif_env_peut_finir(row, col, table)) {
			score += env_peut_finir;
		}

		if (verif_env_emp_finir(row, col, table)) {
			score += env_emp_finir;
		}

		return score;
	}

	Boolean verif_cote(int x, int y, Table_scoring table) {
		if (x % 3 == 1 && y % 3 == 1) {
			return true;
		}
		return false;
	}

	Boolean verif_centre(int x, int y, Table_scoring table) {
		if (x % 3 == 1 || y % 3 == 1) {
			return true;
		}
		return false;
	}

	Boolean verif_finir(int x, int y, Table_scoring table) {
		ArrayList<Integer[][]> in = in_allignes(x, y, table);
		Part_scoring part = table.getPart(x / 3, y / 3);
		for (Integer[][] i : in) {
			int compt = 0;
			for (Integer[] elem : i) {
				compt += part.getCase((x / 3) + elem[0], (y / 3) + elem[1]).getpos();
			}
			if (compt == 2) {
				return true;
			}
		}
		return false;
	}

	Boolean verif_emp_finir(int x, int y, Table_scoring table) {
		ArrayList<Integer[][]> in = in_allignes(x, y, table);
		Part_scoring part = table.getPart(x / 3, y / 3);
		for (Integer[][] i : in) {
			int compt = 0;
			for (Integer[] elem : i) {
				compt += part.getCase(x - (x % 3) + elem[0], y - (y % 3) + elem[1]).getpos();
			}
			if (compt == -2) {
				return true;
			}
		}
		return false;
	}

	Boolean verif_env_finit(int x, int y, Table_scoring table) {
		if (table.getPart(x / 3, y / 3).getpos() != 0) {
			return true;
		}
		return false;
	}

	Boolean verif_env_peut_finir(int x, int y, Table_scoring table) {
		for (Integer[][] alligne : allignes) {
			int compt = 0;
			for (Integer[] pos : alligne) {
				compt += table.getPart(x / 3, y / 3).getCase(x - (x % 3) + pos[0], y - (y % 3) + pos[1]).getpos();
			}
			if (compt == -2) {
				return true;
			}
		}
		return false;
	}

	Boolean verif_env_emp_finir(int x, int y, Table_scoring table) {
		for (Integer[][] alligne : allignes) {
			int compt = 0;
			for (Integer[] pos : alligne) {
				compt += table.getPart(x / 3, y / 3).getCase(x - (x % 3) + pos[0], y - (y % 3) + pos[1]).getpos();
			}
			if (compt == 2) {
				return true;
			}
		}
		return false;
	}

	ArrayList<Integer[][]> in_allignes(int x, int y, Table_scoring table) {
		int x_in_part = x % 3;
		int y_in_part = y % 3;
		int[] position = { x_in_part, y_in_part };
		ArrayList<Integer[][]> in = new ArrayList<Integer[][]>();
		for (Integer[][] alligne : allignes) {
			if (Arrays.asList(alligne).contains(position)) {
				in.add(alligne);
			}
		}
		return in;
	}

}

class Table_scoring {

	ArrayList<Part_scoring> parts;

	Table_scoring(ArrayList<Part_scoring> parts) {
		this.parts = parts;
	}

	ArrayList<Part_scoring> getParts() {
		return parts;
	}

	Part_scoring getPart(int x, int y) {
		for (Part_scoring part : parts) {
			if (x == part.getx() && y == part.gety()) {
				return part;
			}
		}
		return new Part_scoring(-1, -1);
	}

}

class Part_scoring {
	int[][][] allignes = { { { 0, 0 }, { 0, 1 }, { 0, 2 } }, { { 1, 0 }, { 1, 1 }, { 1, 2 } },
			{ { 2, 0 }, { 2, 1 }, { 2, 2 } }, { { 0, 0 }, { 1, 0 }, { 2, 0 } }, { { 0, 1 }, { 1, 1 }, { 2, 1 } },
			{ { 0, 2 }, { 1, 2 }, { 2, 2 } }, { { 0, 0 }, { 1, 1 }, { 2, 2 } }, { { 0, 2 }, { 1, 1 }, { 2, 0 } } };

	int x;
	int y;
	ArrayList<Case_scoring> poss;

	int possession;

	Part_scoring(int x, int y) {
		this.x = x;
		this.y = y;
		possession = 0;

		poss = new ArrayList<Case_scoring>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Case_scoring pos = new Case_scoring(3 * x + i, 3 * y + j);
				poss.add(pos);
			}
		}
	}

	ArrayList<Case_scoring> getposs() {
		return poss;
	}

	Case_scoring getCase(int x, int y) {
		for (Case_scoring pos : poss) {
			if (x == pos.getx() && y == pos.gety()) {
				return pos;
			}
		}
		return new Case_scoring(-1, -1);
	}

	int getx() {
		return x;
	}

	int gety() {
		return y;
	}

	int getpos() {
		return possession;
	}

	void setpos(int possession) {
		this.possession = possession;
	}

	void update_pos() {

		for (int[][] alligne : allignes) {
			int s = 0;
			for (int[] position : alligne) {
				s += this.getCase(position[0] + 3 * this.x, position[1] + 3 * this.y).getpos();
			}
			if (s == 3) {
				this.possession = 1;
				break;
			}
			if (s == -3) {
				this.possession = -1;
				break;
			}
		}

	}

}

class Case_scoring {
	int x;
	int y;

	int quality;
	// 0 cote 1 coin 2 centre

	int pos;
	// 0 personne 1 moi -1 toi

	Case_scoring(int x, int y) {
		this.x = x;
		this.y = y;
		quality = quality(x, y);
		pos = 0;
	}

	int getx() {
		return x;
	}

	int gety() {
		return y;
	}

	int getquality() {
		return quality;
	}

	int getpos() {
		return pos;
	}

	void setpos(int pos) {
		this.pos = pos;
	}

	void degradQuality() {
		quality -= 1;
	}

	void upgradQuality() {
		quality += 1;
	}

	int quality(int x, int y) {
		if (x % 3 == 1 && y % 3 == 1) {
			return 10;
		}
		if (x % 3 == 1 || y % 3 == 1) {
			return 0;
		}
		return 20;
	}
}
