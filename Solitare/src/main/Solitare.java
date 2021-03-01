package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Solitare extends JPanel implements ActionListener, MouseListener {
	
	private JButton reset, AI;
	private Marble board[][] = new Marble[9][9];
	private boolean selected[][] = new boolean[9][9];
	private ArrayList<Integer[]> moves = new ArrayList<Integer[]>();
	private int score = 0, topscore = 20, games = 0;
	
	public Solitare() {
		setLayout(null);
		setBackground(Color.ORANGE.darker());
		
		reset = new JButton("Reset Game");
		add(reset);
		reset.addActionListener(this);
		reset.setBounds(210, 470, 110, 20);
		reset.setVisible(true);
		
		AI = new JButton("Run AI");
		add(AI);
		AI.addActionListener(this);
		AI.setBounds(210, 500, 110, 20);
		AI.setVisible(true);
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (((i < 3 && j >= 3 && j < 6) || (i >= 6 && j >= 3 && j < 6) || (i >= 3 && i < 6)) && !(i == 4 && j == 4)) {
					board[i][j] = new Marble("black.png", (j * 40) + 100, (i * 40) + 100);
					add(board[i][j]);
				}
				
				else {
					board[i][j] = new Marble("x", (j * 40) + 100, (i * 40) + 100);
					add(board[i][j]);
				}
				
				selected[i][j] = false;
			}
		}
		
		addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		setBackground(Color.ORANGE.darker());
		g.setColor(Color.YELLOW);
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (((i < 3 && j >= 3 && j < 6) || (i >= 6 && j >= 3 && j < 6) || (i >= 3 && i < 6))) {
					g.drawOval((j * 40) + 98, (i * 40) + 98, 15, 15);
				}
			}
		}
	}
	
	public boolean jumpPiece(Marble jumper, Marble removed) {
		int xdifference = jumper.geti() - removed.geti();
		int ydifference = jumper.getj() - removed.getj();
		
		int i = jumper.geti() - (xdifference / 2);
		int j = jumper.getj() - (ydifference / 2);
		
		if (((Math.abs(xdifference) == 2 && Math.abs(ydifference) == 0) || (Math.abs(xdifference) == 0 && Math.abs(ydifference) == 2)) && 
				(((i < 3 && j >= 3 && j < 6) || (i >= 6 && j >= 3 && j < 6 && j < 9 && i < 9) || (i >= 3 && i < 6 && i < 9)))) {
			if (board[removed.geti()][removed.getj()].getImage().equals("x") && (board[jumper.geti()][jumper.getj()].getImage().equals("black.png") || board[jumper.geti()][jumper.getj()].getImage().equals("white.png")) && board[i][j].getImage().equals("black.png")) {
				board[i][j].setImage("x");
				board[jumper.geti()][jumper.getj()].setImage("x");
				board[removed.geti()][removed.getj()].setImage("black.png");
				repaint();
				
				return true;
			}
		}
		
		else {
			board[jumper.geti()][jumper.getj()].setImage("black.png");
			repaint();
			
			return false;
		}
		
		return false;
	}
	
	public boolean isMove(Marble jumper, Marble removed) {
		int xdifference = jumper.geti() - removed.geti();
		int ydifference = jumper.getj() - removed.getj();
		
		int i = jumper.geti() - (xdifference / 2), j = jumper.getj() - (ydifference / 2);
		String removedpic = removed.getImage(), jumperpic = jumper.getImage(), middlepic = board[i][j].getImage();
		
		if (((Math.abs(xdifference) == 2 && Math.abs(ydifference) == 0) || (Math.abs(xdifference) == 0 && Math.abs(ydifference) == 2)) && 
				(((i < 3 && j >= 3 && j < 6) || (i >= 6 && j >= 3 && j < 6 && j < 9 && i < 9) || (i >= 3 && i < 6 && i < 9))) &&
				(((jumper.geti() < 3 && jumper.getj() >= 3 && jumper.getj() < 6) || (jumper.geti() >= 6 && jumper.getj() >= 3 && jumper.getj() < 6 && jumper.getj() < 9 && jumper.geti() < 9) || (jumper.geti() >= 3 && jumper.geti() < 6 && jumper.geti() < 9))) &&
				(((removed.geti() < 3 && removed.getj() >= 3 && removed.getj() < 6) || (removed.geti() >= 6 && removed.getj() >= 3 && removed.getj() < 6 && removed.getj() < 9 && removed.geti() < 9) || (removed.geti() >= 3 && removed.geti() < 6 && removed.geti() < 9)))) {
			if (board[removed.geti()][removed.getj()].getImage().equals("x") && board[jumper.geti()][jumper.getj()].getImage().equals("black.png") && board[i][j].getImage().equals("black.png")) {
				return true;
			}
		}
		
		else {
			return false;
		}
		
		return false;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
		boolean found = false;
		int a = 10;
		int b = 10;
		int c = 10;
		int d = 10;
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (selected[i][j]) {
					if (!found) {
						a = i;
						b = j;
						found = true;
					}
				}
			}
		}
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (i == (e.getX() - 80) / 40 && j == (e.getY() - 80) / 40) {
					String file = board[j][i].getImage();
					if (!found && (file.equals("black.png"))) {
						selected[i][j] = true;
						board[j][i].setImage("white.png");
					}
					
					else if (!found) {}
					
					else if (i == a && j == b) {
						selected[i][j] = false;
						board[j][i].setImage("black.png");
						found  = false;
					}
					
					else {
						c = i;
						d = j;
						selected[a][b] = false;
					}
				}
			}
		}
		
		if (c == 10 || d == 10) {
			found = false;
		}
		
		if (found) {
			if (jumpPiece(board[a][b], board[c][d])) {
				selected[a][b] = false;
			}
			
			else {
				selected[a][b] = false;
				board[a][b].setImage("black.png");
				board[c][d].setImage("black.png");
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(reset)) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (((i < 3 && j >= 3 && j < 6) || (i >= 6 && j >= 3 && j < 6) || (i >= 3 && i < 6)) && !(i == 4 && j == 4)) {
						board[i][j].setImage("black.png");
						add(board[i][j]);
					}
					
					else {
						board[i][j].setImage("x");
					}
					
					selected[i][j] = false;
				}
			}
		}
		
		else {
			getMoves();
			repaint();
		}
	}
	
	public void getMoves() {
		while (topscore >= 2) {
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
			while (moves.size() > 0) {
				int move = (int) (Math.random() * moves.size());
				
				jumpPiece(board[moves.get(move)[0]][moves.get(move)[1]], board[moves.get(move)[2]][moves.get(move)[3]]);
				
				System.out.println(moves.get(move)[0] + ", " + moves.get(move)[1] + "  " + moves.get(move)[2] + ", " + moves.get(move)[3]);
				
				for (int i = moves.size(); i > 0; i--) {
					moves.remove(0);
				}
				
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
				
				repaint();
			}
			
			score = 0;
			
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					String pic = board[i][j].getImage();
					
					if (pic.equals("black.png")) {
						score++;
					}
				}
			}
			
			if (score < topscore) topscore = score;
			games++;
			
			System.out.println("Score: " + score);
			System.out.println("Top Score: " + topscore);
			System.out.println("Games: " + games);
			System.out.println();
			
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (((i < 3 && j >= 3 && j < 6) || (i >= 6 && j >= 3 && j < 6) || (i >= 3 && i < 6)) && !(i == 4 && j == 4)) {
						board[i][j].setImage("black.png");
						add(board[i][j]);
					}
					
					else {
						board[i][j].setImage("x");
					}
					
					selected[i][j] = false;
				}
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
	public static void main(String[] args) {
		Solitare gameLogic = new Solitare();
		JFrame x = new JFrame("Solitare");
		x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		x.setResizable(false);
		x.setContentPane(gameLogic);
		x.setSize(531, 600);
		x.repaint();
		x.setVisible(true);
	}
}