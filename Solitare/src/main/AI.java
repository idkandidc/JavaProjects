package main;

import java.util.ArrayList;

import javax.swing.JFrame;

public class AI {
	ArrayList moves = new ArrayList<Integer[]>();
	
	public AI() {
		
	}
	
	/*public void getMoves() {
		Marble board[][] = getBoard();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int a = 0; a < 9; a++) {
					for (int b = 0; b < 9; b++) {
						if (isMove(board[i][j], board[a][b])) {
							Integer temp[] = {i, j, a, b};
							moves.add(temp);
						}
					}
				}
			}
		}
		
		System.out.println(moves);
		
		for (int i = 0; i < 9; i++) {
			Integer temp[] = moves.get(i);
			for (int j = 0; j < 9; j++) {
				System.out.println(temp[j]);
			}
		}
	}*/
}