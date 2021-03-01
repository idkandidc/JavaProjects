package main;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Marble extends JLabel {
	private int x, y;
	private String filename;
	public Marble (String file, int x, int y) {
		this.x = x;
		this.y = y;
		filename = file;
		ImageIcon icon = new ImageIcon(filename); 
		this.setIcon(icon);
		this.setVisible(true);
		this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
	}
	  
	public void move(int dx, int dy) {
		x += dx;
		y += dy;
		this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
	}
	
	public void set(int x, int y) {
		this.y = y;
		this.x = x;
		this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
	}
	
	public int geti() {
		return (x - 100) / 40;
	}
	
	public int getj() {
		return (y - 100) / 40;
	}
	
	public void setImage(String file) {
		filename = file;
		ImageIcon icon = new ImageIcon(filename); 
		this.setIcon(icon);
		this.setVisible(true);
		this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
	}
	
	public String getImage() {
		return filename;
	}
}