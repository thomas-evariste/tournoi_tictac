package Tournoi;

import java.util.ArrayList;
import java.util.Stack;

public class Ia_Alpha_Beta_2 extends IA {
	int alpha = -10000000;
	int beta = 10000000;

	static int[][][] allignes = { { { 0, 0 }, { 0, 1 }, { 0, 2 } }, { { 1, 0 }, { 1, 1 }, { 1, 2 } },
			{ { 2, 0 }, { 2, 1 }, { 2, 2 } }, { { 0, 0 }, { 1, 0 }, { 2, 0 } }, { { 0, 1 }, { 1, 1 }, { 2, 1 } },
			{ { 0, 2 }, { 1, 2 }, { 2, 2 } }, { { 0, 0 }, { 1, 1 }, { 2, 2 } }, { { 0, 2 }, { 1, 1 }, { 2, 0 } } };
	ArrayList<int[]> randomTable;
	int turn;
	int score;
	ArrayList<MinTable> minTables;
	Table_Alpha table;
	boolean Testrow;
	boolean column;

	int var_score_1;
	int var_score_2;
	int var_score_3;
	int var_score_4;
	int var_score_5;
	int var_score_6;
	int var_score_7;
	int var_score_8;
	int var_score_9;

	public Ia_Alpha_Beta_2() {
		randomTable = new ArrayList<int[]>();
		// Scanner in = new Scanner(System.in);
		turn = 0;
		score = 0;
		minTables = new ArrayList<MinTable>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				MinTable minTable = new MinTable(i, j);
				minTables.add(minTable);
			}
		}

		table = new Table_Alpha(minTables);
		Testrow = false;
		column = false;
		var_score_1 = 3275;
		var_score_2 = 9839;
		var_score_3 = 3097;
		var_score_4 = 8054;
		var_score_5 = 841;
		var_score_6 = 337;
		var_score_7 = 3660;
		var_score_8 = 9635;
		var_score_9 = 5973;
	}

	public void reset() {
		randomTable = new ArrayList<int[]>();
		// Scanner in = new Scanner(System.in);
		turn = 0;
		score = 0;
		minTables = new ArrayList<MinTable>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				MinTable minTable = new MinTable(i, j);
				minTables.add(minTable);
			}
		}

		table = new Table_Alpha(minTables);
		Testrow = false;
		column = false;
	}

	public int[] play(Stack<Integer> entre) {
		alpha = -10000000;
		beta = 10000000;
		// randomTable.clear();
		int opponentRow = entre.pop();
		int opponentCol = entre.pop();
		Square square = table.getminTable(opponentRow / 3, opponentCol / 3).getSquare(opponentRow, opponentCol);
		square.setPos(-1);
		MinTable minTable = table.getMinTable(square.getx() / 3, square.gety() / 3);
		minTable.isPossessed();
		// table.updateSquare(square, -1);
		int validActionCount = entre.pop();
		for (int i = 0; i < validActionCount; i++) {
			int row = entre.pop();
			int col = entre.pop();
			//int[] tab = { row, col };
			// randomTable.add(tab);
			// System.err.println("row " + randomTable.get(i)[0] + " column
			// " + randomTable.get(i)[1]);

		}
		int maxScore = -1000000000;
		int v = 0;
		Square chosenSquare = new Square(-1, -1);
		/*
		 * for(MinTable testTables : table.getMinTables()) { for(Square
		 * testSquare : testTables.getPoss()) { // System.err.println("x" + " "
		 * + testSquare.getx() + " y " + testSquare.gety() + " " +
		 * testSquare.getPos()); } }
		 */
		for (Square possibleSquare : table.next_turn(square, -1)) {
//			System.err.println(possibleSquare.getx() + " " +
//			possibleSquare.gety());
			v = alphaBeta(table, 3, possibleSquare);
			// System.err.println("x " +possibleSquare.getx()+" y "
			// +possibleSquare.gety()+" Score " + v);
			if (v > maxScore) {
				maxScore = v;
				chosenSquare = possibleSquare;
			}
		}

		// System.out.println(chosenSquare.getx() + " " + chosenSquare.gety());
		/*
		 * Square chosenSquare= table.getMinTable(randomTable.get(choice)[0]/3 ,
		 * randomTable.get(choice)[1]/3).getSquare(randomTable.get(choice)[ 0] ,
		 * randomTable.get(choice)[1]); score = score(table, chosenSquare);
		 */
		chosenSquare.setPos(1);
		MinTable newMinTable = table.getMinTable(chosenSquare.getx() / 3, chosenSquare.gety() / 3);
		newMinTable.isPossessed();
		// table.updateSquare(chosenSquare, 1);

		turn++;

		int[] retour = { chosenSquare.getx(), chosenSquare.gety() };
		return retour;

	}

	int alphaBeta(Table_Alpha table, int profondeur, Square formerPlay) {
		// si le noeud est une feuille, retourne la valeur

		int v = 0;

		if (profondeur == 1) {
			return score(table, formerPlay);
		} else if (profondeur % 2 == 0) {
			v = +10000000;
			ArrayList<Square> prochainCoups = table.next_turn(formerPlay, -1);
			table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
			for (Square prochainCoup : prochainCoups) {
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
			// 1 ou -1 � v�rifier
			ArrayList<Square> prochainCoups = table.next_turn(formerPlay, 1);
			table.getMinTable(formerPlay.getx() / 3, formerPlay.gety() / 3).isPossessed();
			for (Square prochainCoup : prochainCoups) {
				v = Math.max(v, alphaBeta(table, profondeur - 1, prochainCoup));
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

	int score(Table_Alpha table, Square square) {
		int score = 0;
		int compt = 0;
		int x_part = 0;
		int y_part = 0;
		square.setPos(1);
		MinTable minTable = table.getMinTable(square.getx() % 3, square.gety() % 3);
		minTable.isPossessed();
		ArrayList<MinTable> minTables = table.getMinTables();
		ArrayList<MinTable> dangerousMinTables = new ArrayList<MinTable>();

		// System.err.println("cc");

		// A ajouter : play mene � une case o� l'adversaire poss�de deux cases
		// ou non
		// A ajouter : adversaire ou nous poss�dent deux acro cases align�s
		for (int i = 0; i < minTables.size(); i++) {
			// System.err.println("cc<3");
			// System.err.println("x "+minTables.get(i).getx()+"y
			// "+minTables.get(i).gety()+" "+minTables.get(i).getPos());

			if (minTables.get(i).getPos() != 0) {
				// System.err.println("cc1");
				if (minTables.get(i).getx() == 1 && minTables.get(i).gety() == 1)
					score += minTables.get(i).getPos() * var_score_1;
				else if (minTables.get(i).getx() % 3 == 1 || minTables.get(i).gety() % 3 == 1)
					score += minTables.get(i).getPos() * var_score_2;
				else
					score += minTables.get(i).getPos() * var_score_3;
			}
			// minTable non compl�t�s
			else {

				x_part = minTables.get(i).getx();
				y_part = minTables.get(i).gety();
				for (int[][] alligne : allignes) {
					for (int[] pos : alligne) {
						compt += minTables.get(i)
								.getSquare(minTables.get(i).getx() * 3 + pos[0], minTables.get(i).gety() * 3 + pos[1])
								.getPos();
					}
					if (compt == 2) {
						// System.err.println("ccbis");
						score += var_score_4;
					}
					if (compt == -2) {
						// System.err.println("ccbis2");
						score -= var_score_5;
						if (!dangerousMinTables.contains(minTables.get(i))) {
							dangerousMinTables.add(minTables.get(i));
							// System.err.println("coucou");
						}
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
				score -= var_score_6;
			}
		}
		// si un joueur a deux macros cases align�s ou non
		int posCompt = 0;
		for (int[][] alligne : allignes) {
			for (int[] pos : alligne) {
				posCompt += table.getminTable(pos[0], pos[1]).getPos();
			}
			if (posCompt == 2) {
				// System.err.println("cc3");
				score += var_score_7;
			}
			if (posCompt == -2) {
				// System.err.println("cc4");
				score -= var_score_8;
			}
		}

		// si le play envoit l'adversaire sur une case d�j� gagn�e
		int pos = table.getMinTable(square.getx(), square.gety()).getPos();
		if (pos == 1 || pos == -1) {
			// System.err.println("cc5");
			score -= var_score_9;
		}
		square.setPos(0);
		minTable.isPossessed();
		return score;
	}

}