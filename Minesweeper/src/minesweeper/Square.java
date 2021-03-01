package minesweeper;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Square extends JLabel {
	private int x, y, mines;
	private String file;
	public Square (String filename, int x, int y, int mines) {
		ImageIcon icon = new ImageIcon(filename);
		this.setIcon(icon);
		this.setVisible(true);
		this.x = x;
		this.y = y;
		this.mines = mines;
		this.file = filename;
		this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
	}
	  
	public void move(int dx, int dy) {
		x += dx;
		y += dy;
		this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
	}
	  
	public void setX(int x) {
		this.x = x;
		this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
	}
	  
	public void setY(int y) {
		this.y = y;
		this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
	}
	
	public void setMines(int mines) {
		this.mines = mines;
	}
	
	public void setFile(String file) {
		this.file = file;
		ImageIcon icon = new ImageIcon(file);
		this.setIcon(icon);
	}
	
	public int getX() {
		return x;
	}
	  
	public int getY() {
		return y;
	}
	
	public int getMines() {
		return mines;
	}
	
	public String getFile() {
		return file;
	}
	
	public String toString() {
		return "Name: " + file + ", (x, y): (" + (x / 40) + ", " + (y / 40) + "), Mines: " + mines;
	}
}