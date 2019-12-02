package Tournoi;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Ia_Alpha_Beta_V2_Modulable extends IA {
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

	int var_score_1;
	int var_score_2;
	int var_score_3;
	int var_score_4;
	int var_score_5;
	int var_score_6;
	int var_score_7;
	int var_score_8;
	int var_score_9;
	int var_score_10;
	int var_score_11;
	int var_score_12;
	int var_score_13;
	int var_score_14;
	int var_score_15;
	int var_score_16;
	int var_score_17;
	int var_score_18;
	int var_score_19;
	int var_score_20;
	int var_score_21;
	int var_score_22;
	int var_score_23;
	int var_score_24;
	int var_score_25;
	int var_score_26;
	int var_score_27;
	int var_score_28;

	public Ia_Alpha_Beta_V2_Modulable(int a_1, int a_2, int a_3, int a_4, int a_5, int a_6, int a_7, int a_8, int a_9,
			int a_10, int a_11, int a_12, int a_13, int a_14, int a_15, int a_16, int a_17, int a_18, int a_19,
			int a_20, int a_21, int a_22, int a_23, int a_24, int a_25, int a_26, int a_27, int a_28) {
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

		var_score_1 = a_1;
		var_score_2 = a_2;
		var_score_3 = a_3;
		var_score_4 = a_4;
		var_score_5 = a_5;
		var_score_6 = a_6;
		var_score_7 = a_7;
		var_score_8 = a_8;
		var_score_9 = a_9;
		var_score_10 = a_10;
		var_score_11 = a_11;
		var_score_12 = a_12;
		var_score_13 = a_13;
		var_score_14 = a_14;
		var_score_15 = a_15;
		var_score_16 = a_16;
		var_score_17 = a_17;
		var_score_18 = a_18;
		var_score_19 = a_19;
		var_score_20 = a_20;
		var_score_21 = a_21;
		var_score_22 = a_22;
		var_score_23 = a_23;
		var_score_24 = a_24;
		var_score_25 = a_25;
		var_score_26 = a_26;
		var_score_27 = a_27;
		var_score_28 = a_28;
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
			// System.err.println("x " + possibleSquare.getx() + " y " +
			// possibleSquare.gety() + " Score " + v);
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

		// System.out.println(chose.get(ran).getx() + " " +
		// chose.get(ran).gety());
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

		int[] ret = { chose.get(ran).getx(), chose.get(ran).gety() };

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
				return var_score_1;
			} else if (fin == -1) {
				formerPlay.setPos(0);
				table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
				return -var_score_2;
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
				return var_score_1;
			} else if (fin == -1) {
				formerPlay.setPos(0);
				table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
				return -var_score_2;
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
				return var_score_1;
			} else if (fin == -1) {
				formerPlay.setPos(0);
				table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
				return -var_score_2;
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
				return var_score_1;
			} else if (fin == -1) {
				formerPlay.setPos(0);
				table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
				return -var_score_2;
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
					score += mintable.getPos() * var_score_3;
				else if (mintable.getx() % 3 == 1 || mintable.gety() % 3 == 1)
					score += mintable.getPos() * var_score_4;
				else
					score += mintable.getPos() * var_score_5;
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
						score += var_score_6;
					} else if (posi && !neg) {
						// System.err.println("ccbis");
						score += var_score_7;
					} else if (compt == -2) {
						// System.err.println("ccbis2");
						score -= var_score_8;
						if (!dangerousMinTables.contains(mintable)) {
							dangerousMinTables.add(mintable);
							// System.err.println("coucou");
						}
					} else if (!posi && neg) {
						// System.err.println("ccbis");
						score -= var_score_9;
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
				score -= var_score_10;
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
				score += var_score_11;
			} else if (posCompt == 3) {
				score += var_score_12;
				// System.err.println("cc");
			} else if (posCompt == -2) {
				score -= var_score_13;
			} else if (posCompt == -3) {
				score -= var_score_14;
				// System.err.println("cc");
			}
		}

		// si le play envoit l'adversaire sur une case déjà gagnée
		int pos = table.getMinTable(square.getx(), square.gety()).getPos();
		if (pos == 1 || pos == -1) {
			// System.err.println("cc5");
			score -= var_score_15;
		}
		square.setPos(0);
		minTable.isPossessed();
		return score;
	}

	int scoreInv(Table_2 table, Square_2 square) {
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
					score += mintable.getPos() * var_score_16;
				else if (mintable.getx() % 3 == 1 || mintable.gety() % 3 == 1)
					score += mintable.getPos() * var_score_17;
				else
					score += mintable.getPos() * var_score_18;
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
						score += var_score_19;
					} else if (posi && !neg) {
						// System.err.println("ccbis");
						score += var_score_20;
					} else if (compt == -2) {
						// System.err.println("ccbis2");
						score -= var_score_21;
						if (!dangerousMinTables.contains(mintable)) {
							dangerousMinTables.add(mintable);
							// System.err.println("coucou");
						}
					} else if (!posi && neg) {
						// System.err.println("ccbis");
						score -= var_score_22;
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
				score += var_score_23;
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
				score += var_score_24;
			} else if (posCompt == 3) {
				// System.err.println("cc");
				score += var_score_25;
			} else if (posCompt == -2) {
				score -= var_score_26;
			} else if (posCompt == -3) {
				score -= var_score_27;
			}
		}

		// si le play envoit l'adversaire sur une case déjà gagnée
		int pos = table.getMinTable(square.getx(), square.gety()).getPos();
		if (pos == 1 || pos == -1) {
			// System.err.println("cc5");
			score += var_score_28;
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