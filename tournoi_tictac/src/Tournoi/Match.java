package Tournoi;

import java.util.ArrayList;
import java.util.Stack;

public class Match {

	public int play(IA J1, IA J2) {

		Table_match t = new Table_match();

		Stack<Integer> entre = new Stack<Integer>();
		int[] pre = { -1, -1 };
		Boolean fini = false;
		int J_actif = 1;

		Boolean[] fin = { false, false };
		
		int compt = 1;
		
		while (!fini) {
			
			
			entre.clear();
			case_pos(t, entre, pre);
			if(entre.peek()==0){
				return 0;
			}
			entre.push(pre[1]);
			entre.push(pre[0]);

			System.err.println("Stack: " + entre);
			System.err.println("compt: " + compt);
			
			if (J_actif == 1) {
				pre = J1.play(entre);
			} else {
				pre = J2.play(entre);
			}
			fin = update_case(t, pre, J_actif);

			//System.out.println("le joueur " + J_actif + " a joué en " + pre[0] + " : " + pre[1]);

			fini = fin[0];
			J_actif = (J_actif + 1);
			if (J_actif > 2) {
				J_actif = 1;
			}
			
			compt++;
		}

		if (fin[1]) {
			return J_actif;
		} else {
			J_actif = (J_actif + 1);
			if (J_actif > 2) {
				J_actif = 1;
			}
			return J_actif;
		}
	}

	private void case_pos(Table_match t, Stack<Integer> entre, int[] pre) {

		int compt =0;
		if (pre[0] == -1) {
			ArrayList<Part_match> parts = t.getParts();
			for (Part_match part2 : parts) {

				ArrayList<Case_match> cases = part2.getposs();
				for (Case_match cas : cases) {

					entre.push(cas.gety());
					entre.push(cas.getx());
					compt++;
				}

			}
			
		} else {

			Part_match part = t.getPart(pre[0]%3, pre[1]%3);

			if (part.getpos() == 0) {
				ArrayList<Case_match> cases = part.getposs();
				for (Case_match cas : cases) {
					if (cas.getpos() == 0) {
						entre.push(cas.getx());
						entre.push(cas.gety());
						compt++;
					}
				}
			} else {
				ArrayList<Part_match> parts = t.getParts();
				for (Part_match part2 : parts) {
					if (part2.getpos() == 0) {
						ArrayList<Case_match> cases = part2.getposs();
						for (Case_match cas : cases) {
							if (cas.getpos() == 0) {
								entre.push(cas.getx());
								entre.push(cas.gety());
								compt++;
							}
						}
					}
				}
			}
		}
		entre.push(compt);

	}

	private Boolean[] update_case(Table_match t, int[] pre, int J_actif) {
		Boolean[] fin = { false, false };

		Part_match part = t.getPart_by_Case(pre[0], pre[1]);
		Case_match cas = part.getCase(pre[0], pre[1]);

		if (cas.getpos() != 0 || part.getpos() != 0) {
			fin[0] = true;
			fin[1] = true;
			//System.out.println("le joueur " + J_actif + " a fait une erreur");
			//System.err.println(cas.getpos() + " : " + part.getpos());
			return fin;
		}

		int possession = J_actif;
		if (possession == 2) {
			possession = -1;
		}
		cas.setpos(possession);

		part.update_pos();
		if (part.getpos() != 0) {
			if (t.victoir()) {
				fin[0] = true;
			}
		}

		return fin;
	}

}

class Table_match {
	int[][][] allignes = { { { 0, 0 }, { 0, 1 }, { 0, 2 } }, { { 1, 0 }, { 1, 1 }, { 1, 2 } },
			{ { 2, 0 }, { 2, 1 }, { 2, 2 } }, { { 0, 0 }, { 1, 0 }, { 2, 0 } }, { { 0, 1 }, { 1, 1 }, { 2, 1 } },
			{ { 0, 2 }, { 1, 2 }, { 2, 2 } }, { { 0, 0 }, { 1, 1 }, { 2, 2 } }, { { 0, 2 }, { 1, 1 }, { 2, 0 } } };

	ArrayList<Part_match> parts;

	Table_match() {

		parts = new ArrayList<Part_match>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Part_match part = new Part_match(i, j);
				parts.add(part);
			}
		}
	}

	ArrayList<Part_match> getParts() {
		return parts;
	}

	Part_match getPart(int x, int y) {
		for (Part_match part : parts) {
			if (x == part.getx() && y == part.gety()) {
				return part;
			}
		}
		return new Part_match(-1, -1);
	}

	Part_match getPart_by_Case(int x, int y) {
		for (Part_match part : parts) {
			if ((x / 3) == part.getx() && (y / 3) == part.gety()) {
				return part;
			}
		}
		return new Part_match(-1, -1);
	}

	boolean victoir() {

		for (int[][] alligne : allignes) {
			int s = 0;
			for (int[] position : alligne) {
				s += this.getPart(position[0], position[1]).getpos();
			}
			if (s == 3 || s == -3) {
				return true;
			}
		}

		return false;
	}

}

class Part_match {
	int[][][] allignes = { { { 0, 0 }, { 0, 1 }, { 0, 2 } }, { { 1, 0 }, { 1, 1 }, { 1, 2 } },
			{ { 2, 0 }, { 2, 1 }, { 2, 2 } }, { { 0, 0 }, { 1, 0 }, { 2, 0 } }, { { 0, 1 }, { 1, 1 }, { 2, 1 } },
			{ { 0, 2 }, { 1, 2 }, { 2, 2 } }, { { 0, 0 }, { 1, 1 }, { 2, 2 } }, { { 0, 2 }, { 1, 1 }, { 2, 0 } } };

	int x;
	int y;
	ArrayList<Case_match> poss;

	int possession;
	// 0 personne 1 J1 -1 J2

	Part_match(int x, int y) {
		this.x = x;
		this.y = y;
		possession = 0;

		poss = new ArrayList<Case_match>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Case_match pos = new Case_match(3 * x + i, 3 * y + j);
				poss.add(pos);
			}
		}
	}

	ArrayList<Case_match> getposs() {
		return poss;
	}

	Case_match getCase(int x, int y) {
		for (Case_match pos : poss) {
			if (x == pos.getx() && y == pos.gety()) {
				return pos;
			}
		}
		return new Case_match(-1, -1);
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
				// System.err.print(position[0]+3*this.x);
				// System.err.print(":");
				// System.err.print(position[1]+3*this.y);
				// System.err.print(":");
				// System.err.print(this.getCase(position[0]+3*this.x,
				// position[1]+3*this.y).getpos());
				// System.err.print(" ");
			}
			// System.err.println(" s="+s);
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

class Case_match {
	int x;
	int y;

	int possession;
	// 0 personne 1 J1 -1 J2

	Case_match(int x, int y) {
		this.x = x;
		this.y = y;
		possession = 0;
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

}
