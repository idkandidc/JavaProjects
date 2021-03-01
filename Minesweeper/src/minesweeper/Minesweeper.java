package minesweeper;

import javax.swing.JFrame;

public class Minesweeper {
	public static void main(String[] args) {
		Logic gameLogic = new Logic();
		JFrame x = new JFrame("Minesweeper");
		x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		x.setResizable(true);
		x.setContentPane(gameLogic);
		x.setSize(300, 300);
		x.repaint();
		x.setVisible(true);
	}
}