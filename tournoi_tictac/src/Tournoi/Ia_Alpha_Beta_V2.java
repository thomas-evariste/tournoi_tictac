package Tournoi;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class Ia_Alpha_Beta_V2 extends IA {
	int alpha = -100000;
	int beta = 100000;

	static int[][][] allignes = { { { 0, 0 }, { 0, 1 }, { 0, 2 } }, { { 1, 0 }, { 1, 1 }, { 1, 2 } },
			{ { 2, 0 }, { 2, 1 }, { 2, 2 } }, { { 0, 0 }, { 1, 0 }, { 2, 0 } }, { { 0, 1 }, { 1, 1 }, { 2, 1 } },
			{ { 0, 2 }, { 1, 2 }, { 2, 2 } }, { { 0, 0 }, { 1, 1 }, { 2, 2 } }, { { 0, 2 }, { 1, 1 }, { 2, 0 } } };

	ArrayList<int[]> randomTable;
	Random rand;
	int turn;
	int score;
	ArrayList<MinTable_2> minTables;
	Table_2 table;
	ArrayList<Square_2> chose;
	Square_2 chosenSquare;
	int row;
	int col;
	MinTable_2 newMinTable;

	public Ia_Alpha_Beta_V2() {
		randomTable = new ArrayList<int[]>();
		rand = new Random();
		turn = 0;
		score = 0;
		minTables = new ArrayList<MinTable_2>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				MinTable_2 minTable = new MinTable_2(i, j);
				minTables.add(minTable);
			}
		}

		table = new Table_2(minTables);
		chose = new ArrayList<Square_2>();
		chosenSquare = new Square_2(-1, -1);
	}

	public void reset() {
		randomTable = new ArrayList<int[]>();
		rand = new Random();
		turn = 0;
		score = 0;
		minTables = new ArrayList<MinTable_2>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				MinTable_2 minTable = new MinTable_2(i, j);
				minTables.add(minTable);
			}
		}

		table = new Table_2(minTables);
		chose = new ArrayList<Square_2>();
		chosenSquare = new Square_2(-1, -1);
	}

	public int[] play(Stack<Integer> entre) {
		alpha = -100000;
		beta = 100000;
		// randomTable.clear();
		int opponentRow = entre.pop();
		int opponentCol = entre.pop();
		Square_2 square = table.getMinTable(opponentRow / 3, opponentCol / 3).getSquare(opponentRow, opponentCol);
		square.setPos(-1);
		MinTable_2 minTable = table.getMinTable(square.getx() / 3, square.gety() / 3);
		minTable.isPossessed();
		// table.updateSquare(square, -1);
		int validActionCount = entre.pop();
		for (int i = 0; i < validActionCount; i++) {
			row = entre.pop();
			col = entre.pop();
			// int[] tab = {row,col};
			// randomTable.add(tab);
			// System.err.println("row " + randomTable.get(i)[0] + " column
			// " + randomTable.get(i)[1]);

		}
		int maxScore = -100000000;
		int v = 0;
		chose.clear();
		chose.add(chosenSquare);
		/*
		 * for(MinTable testTables : table.getMinTables()) { for(Square
		 * testSquare : testTables.getPoss()) { // System.err.println("x" + " "
		 * + testSquare.getx() + " y " + testSquare.gety() + " " +
		 * testSquare.getPos()); } }
		 */
		for (Square_2 possibleSquare : table.next_turn(square, -1)) {
			// System.err.println(possibleSquare.getx() + " " +
			// possibleSquare.gety());
			possibleSquare.setPos(1);
			newMinTable = table.getMinTable(possibleSquare.getx() / 3, possibleSquare.gety() / 3);
			newMinTable.isPossessed();
			if (est_terminer(table) == 1) {
				chose.clear();
				chose.add(possibleSquare);
				break;
			} else if (validActionCount < 10) {
				v = alphaBetaInv(table, 4, possibleSquare);
			} else {
				v = alphaBeta(table, 3, possibleSquare);
			}
			//System.err.println("x " + possibleSquare.getx() + " y " + possibleSquare.gety() + " Score " + v);
			if (v > maxScore) {
				chose.clear();
				maxScore = v;
				chose.add(possibleSquare);
			} else if (v == maxScore) {
				chose.add(possibleSquare);
			}
		}
		if (opponentRow == -1) {
			chose.clear();
			chose.add(table.getMinTable(1, 1).getSquare(4, 4));
		}

		int ran = rand.nextInt(chose.size());

		//System.out.println(chose.get(ran).getx() + " " + chose.get(ran).gety());
		/*
		 * Square chosenSquare= table.getMinTable(randomTable.get(choice)[0]/3 ,
		 * randomTable.get(choice)[1]/3).getSquare(randomTable.get(choice)[ 0] ,
		 * randomTable.get(choice)[1]); score = score(table, chosenSquare);
		 */
		chose.get(ran).setPos(1);
		newMinTable = table.getMinTable(chose.get(ran).getx() / 3, chose.get(ran).gety() / 3);
		newMinTable.isPossessed();
		// table.updateSquare(chosenSquare, 1);

		turn++;
		
		int[] ret ={chose.get(ran).getx(),chose.get(ran).gety()};
		
		return ret;

	}

	int alphaBeta(Table_2 table, int profondeur, Square_2 formerPlay) {
		// si le noeud est une feuille, retourne la valeur

		int v = 0;

		if (profondeur == 1) {
			return score(table, formerPlay);
		} else if (profondeur % 2 == 0) {
			v = +10000000;
			ArrayList<Square_2> prochainCoups = table.next_turn(formerPlay, 1);
			table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
			int fin = est_terminer(table);
			if (fin == 1) {
				formerPlay.setPos(0);
				table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
				return 70000;
			} else if (fin == -1) {
				formerPlay.setPos(0);
				table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
				return -70000;
			}
			for (Square_2 prochainCoup : prochainCoups) {
				v = Math.min(v, alphaBeta(table, profondeur - 1, prochainCoup));
				// System.err.println("Square :"+prochainCoup.getx()+"
				// "+prochainCoup.gety()+" profondeur: "+profondeur+" v: "+v);
				if (alpha >= v) {
					formerPlay.setPos(0);
					table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
					return v;
				}
				beta = Math.min(beta, v);

			}
			formerPlay.setPos(0);
		}

		else {
			v = -10000000;
			// 1 ou -1 à vérifier
			ArrayList<Square_2> prochainCoups = table.next_turn(formerPlay, -1);
			table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
			int fin = est_terminer(table);
			if (fin == 1) {
				formerPlay.setPos(0);
				table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
				return 70000;
			} else if (fin == -1) {
				formerPlay.setPos(0);
				table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
				return -70000;
			}
			for (Square_2 prochainCoup : prochainCoups) {
				v = Math.max(v, alphaBeta(table, profondeur - 1, prochainCoup));
				// System.err.println("SquareF :"+formerPlay.getx()+"
				// "+formerPlay.gety()+" SquareP :"+prochainCoup.getx()+"
				// "+prochainCoup.gety()+" profondeur: "+profondeur+" v: "+v+"
				// beta: "+beta);
				if (v >= beta) {
					formerPlay.setPos(0);
					table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
					return v;
				}
				alpha = Math.max(alpha, v);

			}
			formerPlay.setPos(0);

		}
		table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();

		return v;

	}

	int alphaBetaInv(Table_2 table, int profondeur, Square_2 formerPlay) {
		// si le noeud est une feuille, retourne la valeur

		int v = 0;

		if (profondeur == 1) {
			return scoreInv(table, formerPlay);
		} else if (profondeur % 2 == 0) {
			v = +10000000;
			ArrayList<Square_2> prochainCoups = table.next_turn(formerPlay, 1);
			table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
			int fin = est_terminer(table);
			if (fin == 1) {
				formerPlay.setPos(0);
				table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
				return 70000;
			} else if (fin == -1) {
				formerPlay.setPos(0);
				table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
				return -70000;
			}
			for (Square_2 prochainCoup : prochainCoups) {
				v = Math.min(v, alphaBetaInv(table, profondeur - 1, prochainCoup));
				// System.err.println("Square :"+prochainCoup.getx()+"
				// "+prochainCoup.gety()+" profondeur: "+profondeur+" v: "+v);
				if (alpha >= v) {
					formerPlay.setPos(0);
					table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
					return v;
				}
				beta = Math.min(beta, v);

			}
			formerPlay.setPos(0);
		}

		else {
			v = -10000000;
			// 1 ou -1 à vérifier
			ArrayList<Square_2> prochainCoups = table.next_turn(formerPlay, -1);
			table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
			int fin = est_terminer(table);
			if (fin == 1) {
				formerPlay.setPos(0);
				table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
				return 70000;
			} else if (fin == -1) {
				formerPlay.setPos(0);
				table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
				return -70000;
			}
			for (Square_2 prochainCoup : prochainCoups) {
				v = Math.max(v, alphaBetaInv(table, profondeur - 1, prochainCoup));
				// System.err.println("Square :"+prochainCoup.getx()+"
				// "+prochainCoup.gety()+" profondeur: "+profondeur+" v: "+v);
				if (v >= beta) {
					formerPlay.setPos(0);
					table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
					return v;
				}
				alpha = Math.max(alpha, v);

			}
			formerPlay.setPos(0);

		}
		table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();

		return v;

	}

	int score(Table_2 table, Square_2 square) {
		Boolean posi = false;
		Boolean neg = false;
		int uti = 0;
		int score = 0;
		int compt = 0;
		int x_part = 0;
		int y_part = 0;
		square.setPos(1);
		MinTable_2 minTable = table.getMinTable(square.getx() % 3, square.gety() % 3);
		minTable.isPossessed();
		ArrayList<MinTable_2> minTables = table.getMinTables();
		ArrayList<MinTable_2> dangerousMinTables = new ArrayList<MinTable_2>();

		// System.err.println("cc");

		// A ajouter : play mene à une case où l'adversaire possède deux cases
		// ou non
		// A ajouter : adversaire ou nous possédent deux acro cases alignés
		for (MinTable_2 mintable : minTables) {
			// System.err.println("cc<3");
			// System.err.println("x "+minTables.get(i).getx()+"y
			// "+minTables.get(i).gety()+" "+minTables.get(i).getPos());

			if (mintable.getPos() != 0) {
				// System.err.println("cc1");
				if (mintable.getx() == 1 && mintable.gety() == 1)
					score += mintable.getPos() * 500;
				else if (mintable.getx() % 3 == 1 || mintable.gety() % 3 == 1)
					score += mintable.getPos() * 200;
				else
					score += mintable.getPos() * 300;
			}
			// minTable non complétés
			else {

				for (int[][] alligne : allignes) {
					posi = false;
					neg = false;
					for (int[] pos : alligne) {
						uti = mintable.getSquare(mintable.getx() * 3 + pos[0], mintable.gety() * 3 + pos[1]).getPos();
						compt += uti;
						if (uti == 1) {
							posi = true;
						}
						if (uti == -1) {
							neg = true;
						}
					}
					if (compt == 2) {
						// System.err.println("ccbis");
						score += 10;
					} else if (posi && !neg) {
						// System.err.println("ccbis");
						score += 1;
					} else if (compt == -2) {
						// System.err.println("ccbis2");
						score -= 15;
						if (!dangerousMinTables.contains(mintable)) {
							dangerousMinTables.add(mintable);
							// System.err.println("coucou");
						}
					} else if (!posi && neg) {
						// System.err.println("ccbis");
						score -= 1;
					}
					compt = 0;

				}
			}
		}
		// si l'on envoit l'adversaire sur une case qu'il peut terminer
		for (int j = 0; j < dangerousMinTables.size(); j++) {
			// System.err.println("coucou2");
			if (square.getx() % 3 == dangerousMinTables.get(j).getx()
					&& (square.gety() % 3 == dangerousMinTables.get(j).gety())) {
				// System.err.println("cc2");
				score -= 50;
			}
		}
		// si un joueur a deux macros cases alignés ou non
		int posCompt = 0;
		for (int[][] alligne : allignes) {
			posCompt = 0;
			for (int[] pos : alligne) {
				posCompt += table.getMinTable(pos[0], pos[1]).getPos();
			}
			if (posCompt == 2) {
				score += 700;
			} else if (posCompt == 3) {
				score += 70000;
				//System.err.println("cc");
			} else if (posCompt == -2) {
				score -= 700;
			} else if (posCompt == -3) {
				score -= 70000;
				//System.err.println("cc");
			}
		}

		// si le play envoit l'adversaire sur une case déjà gagnée
		int pos = table.getMinTable(square.getx(), square.gety()).getPos();
		if (pos == 1 || pos == -1) {
			// System.err.println("cc5");
			score -= 75;
		}
		square.setPos(0);
		minTable.isPossessed();
		return score;
	}

	static int scoreInv(Table_2 table, Square_2 square) {
		Boolean posi = false;
		Boolean neg = false;
		int uti = 0;
		int score = 0;
		int compt = 0;
		int x_part = 0;
		int y_part = 0;
		square.setPos(-1);
		MinTable_2 minTable = table.getMinTable(square.getx() % 3, square.gety() % 3);
		minTable.isPossessed();
		ArrayList<MinTable_2> minTables = table.getMinTables();
		ArrayList<MinTable_2> dangerousMinTables = new ArrayList<MinTable_2>();

		// System.err.println("cc");

		// A ajouter : play mene à une case où l'adversaire possède deux cases
		// ou non
		// A ajouter : adversaire ou nous possédent deux acro cases alignés
		for (MinTable_2 mintable : minTables) {
			// System.err.println("cc<3");
			// System.err.println("x "+minTables.get(i).getx()+"y
			// "+minTables.get(i).gety()+" "+minTables.get(i).getPos());

			if (mintable.getPos() != 0) {
				// System.err.println("cc1");
				if (mintable.getx() == 1 && mintable.gety() == 1)
					score += mintable.getPos() * 500;
				else if (mintable.getx() % 3 == 1 || mintable.gety() % 3 == 1)
					score += mintable.getPos() * 200;
				else
					score += mintable.getPos() * 300;
			}
			// minTable non complétés
			else {
				for (int[][] alligne : allignes) {
					posi = false;
					neg = false;
					for (int[] pos : alligne) {
						uti = mintable.getSquare(mintable.getx() * 3 + pos[0], mintable.gety() * 3 + pos[1]).getPos();
						compt += uti;
						if (uti == 1) {
							posi = true;
						}
						if (uti == -1) {
							neg = true;
						}
					}
					if (compt == 2) {
						// System.err.println("ccbis");
						score += 10;
					} else if (posi && !neg) {
						// System.err.println("ccbis");
						score += 1;
					} else if (compt == -2) {
						// System.err.println("ccbis2");
						score -= 15;
						if (!dangerousMinTables.contains(mintable)) {
							dangerousMinTables.add(mintable);
							// System.err.println("coucou");
						}
					} else if (!posi && neg) {
						// System.err.println("ccbis");
						score -= 1;
					}
					compt = 0;

				}
			}
		}
		// si l'on envoit l'adversaire sur une case qu'il peut terminer
		for (int j = 0; j < dangerousMinTables.size(); j++) {
			// System.err.println("coucou2");
			if (square.getx() % 3 == dangerousMinTables.get(j).getx()
					&& (square.gety() % 3 == dangerousMinTables.get(j).gety())) {
				// System.err.println("cc2");
				score += 50;
			}
		}
		// si un joueur a deux macros cases alignés ou non
		int posCompt = 0;
		for (int[][] alligne : allignes) {
			posCompt = 0;
			for (int[] pos : alligne) {
				posCompt += table.getMinTable(pos[0], pos[1]).getPos();
			}
			if (posCompt == 2) {
				score += 700;
			} else if (posCompt == 3) {
				//System.err.println("cc");
				score += 70000;
			} else if (posCompt == -2) {
				score -= 700;
			} else if (posCompt == -3) {
				score -= 70000;
			}
		}

		// si le play envoit l'adversaire sur une case déjà gagnée
		int pos = table.getMinTable(square.getx(), square.gety()).getPos();
		if (pos == 1 || pos == -1) {
			// System.err.println("cc5");
			score += 75;
		}
		square.setPos(0);
		minTable.isPossessed();
		return score;
	}

	static int est_terminer(Table_2 table) {
		int posCompt = 0;
		for (int[][] alligne : allignes) {
			posCompt = 0;
			for (int[] pos : alligne) {
				posCompt += table.getMinTable(pos[0], pos[1]).getPos();
			}
			if (posCompt == 3) {
				return 1;
			} else if (posCompt == -3) {
				return -1;
			}
		}
		return 0;
	}

}

class Square_2 {
	int x;
	int y;

	int pos;
	// 0 personne 1 moi -1 toi

	Square_2(int x, int y) {
		this.x = x;
		this.y = y;
		pos = 0;
	}

	int getx() {
		return x;
	}

	int gety() {
		return y;
	}

	int getPos() {
		return pos;
	}

	void setPos(int pos) {
		this.pos = pos;
	}

}

class MinTable_2 {
	int[][][] allignes = { { { 0, 0 }, { 0, 1 }, { 0, 2 } }, { { 1, 0 }, { 1, 1 }, { 1, 2 } },
			{ { 2, 0 }, { 2, 1 }, { 2, 2 } }, { { 0, 0 }, { 1, 0 }, { 2, 0 } }, { { 0, 1 }, { 1, 1 }, { 2, 1 } },
			{ { 0, 2 }, { 1, 2 }, { 2, 2 } }, { { 0, 0 }, { 1, 1 }, { 2, 2 } }, { { 0, 2 }, { 1, 1 }, { 2, 0 } } };

	int x;
	int y;
	ArrayList<Square_2> poss;
	int pos;

	MinTable_2(int x, int y) {
		this.x = x;
		this.y = y;

		poss = new ArrayList<Square_2>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Square_2 pos = new Square_2(3 * x + i, 3 * y + j);
				poss.add(pos);
			}
		}
	}

	ArrayList<Square_2> getPoss() {
		return poss;
	}

	Square_2 getSquare(int x, int y) {
		for (Square_2 pos : poss) {
			if (x == pos.getx() && y == pos.gety()) {
				return pos;
			}
		}
		return new Square_2(-1, -1);
	}

	int getx() {
		return x;
	}

	int gety() {
		return y;
	}

	int getPos() {
		return pos;
	}

	void isPossessed() {
		int compt = 0;
		int x_part = this.getx();
		int y_part = this.gety();
		Boolean posi = false;
		for (int[][] alligne : allignes) {
			compt = 0;
			for (int[] pos : alligne) {
				compt += this.getSquare(x_part * 3 + pos[0], y_part * 3 + pos[1]).getPos();
			}
			if (compt == 3) {
				this.setPos(1);
				posi = true;
			} else if (compt == -3) {
				this.setPos(-1);
				posi = true;
			}
			compt = 0;
		}
		if (!posi) {
			this.setPos(0);
		}

		// System.err.println("REGARDE ICI x " +x_part +" y " + y_part+"
		// "+this.getPos());

	}

	void setPos(int pos) {
		this.pos = pos;
	}

}

class Table_2 {

	ArrayList<MinTable_2> minTables;

	Table_2(ArrayList<MinTable_2> minTables) {
		this.minTables = minTables;
	}

	ArrayList<MinTable_2> getMinTables() {
		return minTables;
	}

	MinTable_2 getMinTable(int x, int y) {
		for (MinTable_2 minTable : minTables) {
			if (x == minTable.getx() && y == minTable.gety()) {
				return minTable;
			}
		}
		return new MinTable_2(-1, -1);
	}

	ArrayList<Square_2> next_turn(Square_2 formerPlay, int activePlayer) {
		ArrayList<Square_2> next_turn = new ArrayList<Square_2>();
		formerPlay.setPos(activePlayer);
		MinTable_2 minTable = this.getMinTable(formerPlay.getx() % 3, formerPlay.gety() % 3);
		if (minTable.getPos() == 0 && formerPlay.getx() != -1) {
			for (Square_2 square : minTable.getPoss()) {
				// System.err.println("x: "+square.getx()+" y: "+square.gety()+"
				// pos: "+square.getPos());
				if (square.getPos() == 0) {
					next_turn.add(square);
				}
			}
		} else {
			for (MinTable_2 macroTable : this.getMinTables()) {
//				System.err.println(
//						"x: " + macroTable.getx() + " y: " + macroTable.gety() + " pos: " + macroTable.getPos());
				if (macroTable.getPos() == 0) {
					for (Square_2 square : macroTable.getPoss()) {
						if (square.getPos() == 0) {
							next_turn.add(square);
						}
					}
				}
			}
		}
		return next_turn;
	}

}