package minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Logic extends JPanel implements MouseListener, ActionListener {
	private Square squares[][] = new Square[100][100];
	private JButton resetGame, enter, easy, medium, hard, custom;
	private JTextField inputX, inputY, inputMines;
	private JLabel xText, yText, minesText, timer, mine;
	private Timer t = new Timer(100, this);
	private String winState = "none";
	private boolean boardSetUp = false, gameOver = false, win = true, zero = false, 
			timerGo = false, firstPress = false;
	private int x = 10, y = 10, mines = -1, counter = 0, total = 0;
	
	public Logic() {
		setLayout(null);
		
		//place all the buttons on the screen
		resetGame = new JButton("New Game");
		add(resetGame);
		resetGame.addActionListener(this);
		resetGame.setBounds(100, 100, 100, 20);
		resetGame.setVisible(false);
		
		enter = new JButton("Start Game");
		add(enter);
		enter.addActionListener(this);
		enter.setBounds(100, 200, 100, 20);
		enter.setVisible(false);
		
		easy = new JButton("5 x 5");
		add(easy);
		easy.addActionListener(this);
		easy.setBounds(100, 50, 100, 20);
		easy.setVisible(true);
		
		medium = new JButton("16 x 8");
		add(medium);
		medium.addActionListener(this);
		medium.setBounds(100, 100, 100, 20);
		medium.setVisible(true);
		
		hard = new JButton("32 x 16");
		add(hard);
		hard.addActionListener(this);
		hard.setBounds(100, 150, 100, 20);
		hard.setVisible(true);
		
		custom = new JButton("Custom");
		add(custom);
		custom.addActionListener(this);
		custom.setBounds(100, 200, 100, 20);
		custom.setVisible(true);
		
		//add the text feilds for the custom option
		inputX = new JTextField();
		add(inputX);
		inputX.addActionListener(this);
		inputX.setBounds(180, 50, 50, 20);
		inputX.setVisible(false);
		
		inputY = new JTextField();
		add(inputY);
		inputY.addActionListener(this);
		inputY.setBounds(180, 100, 50, 20);
		inputY.setVisible(false);
		
		inputMines = new JTextField();
		add(inputMines);
		inputMines.addActionListener(this);
		inputMines.setBounds(180, 150, 50, 20);
		inputMines.setVisible(false);
		
		//add the description labels for the custom option
		xText = new JLabel("Number of squares across:");
		add(xText);
		xText.setBounds(20, 50, 200, 20);
		xText.setVisible(false);
		
		yText = new JLabel("Number of squares down:");
		add(yText);
		yText.setBounds(20, 100, 150, 20);
		yText.setVisible(false);
		
		minesText = new JLabel("Number of mines on board:");
		add(minesText);
		minesText.setBounds(20, 150, 200, 20);
		minesText.setVisible(false);
		
		//add the timer label
		timer = new JLabel("Time: ");
		add(timer);
		timer.setBounds(100, 150, 100, 20);
		timer.setVisible(false);
		
		//add the mines label
		mine = new JLabel("Mines: ");
		add(mine);
		mine.setBounds(100, 150, 100, 20);
		mine.setVisible(false);
		
		//add the mouse listener
		addMouseListener(this);
		setFocusable(true);
		
		setBackground(Color.GRAY);
		
		//start the timer
		t.start();
		
		//initialize all of the squares (non visibly)
		for (int j = 0; j < 100; j++) {
    		for (int k = 0; k < 100; k++) {
    			squares[j][k] = new Square("blank.png", (j * 40) + 10, (k * 40) + 10, 0);
    			add(squares[j][k]);
    			squares[j][k].setVisible(false);
	    	}
    	}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		//if it is the "Reset Game" button
		if (e.getSource().equals(resetGame)) {
			resetGame.setVisible(false);
			inputX.setVisible(false);
			inputY.setVisible(false);
			inputMines.setVisible(false);
			boardSetUp = false;
			easy.setVisible(true);
			medium.setVisible(true);
			hard.setVisible(true);
			custom.setVisible(true);
			enter.setVisible(false);
			xText.setVisible(false);
			yText.setVisible(false);
			minesText.setVisible(false);
			timer.setVisible(false);
			mine.setVisible(false);
			timerGo = false;
			firstPress = false;
			winState = "none";
			repaint();
			
			//reset the state of the squares that were used in the game
			for (int j = 0; j < x; j++) {
	    		for (int k = 0; k < y; k++) {
	    			squares[j][k].setFile("blank.png");
	    			squares[j][k].setMines(0);
		    		squares[j][k].setVisible(false);
		    	}
	    	}
		}
		
		//if it is the easy button
		else if (e.getSource().equals(easy)) {
			resetGame.setVisible(true);
			inputX.setVisible(false);
			inputY.setVisible(false);
			inputMines.setVisible(false);
			boardSetUp = true;
			easy.setVisible(false);
			medium.setVisible(false);
			hard.setVisible(false);
			custom.setVisible(false);
			enter.setVisible(false);
			xText.setVisible(false);
			yText.setVisible(false);
			minesText.setVisible(false);
			timer.setVisible(true);
			mine.setVisible(true);
			counter = 0;
			timerGo = true;
			firstPress = true;
			winState = "none";
			repaint();
			
			//parameters for the size of the board
			x = 5;
			y = 5;
			mines = 5;
			total = x * y;
			
			//place the "Reset Game" button and the "Time: _:__" in the correct position
			resetGame.setBounds(((x * 40)/2) - 70, (y * 40) + 20, 160, 20);
			timer.setBounds(((x * 40)/2) - 20, (y * 40) + 60, 160, 20);
			mine.setBounds(((x * 40)/2) - 18, (y * 40) + 80, 160, 20);
			
			//set up the board
			setUpBoard();
		}
		
		//if it is the medium button
		else if (e.getSource().equals(medium)) {
			resetGame.setVisible(true);
			inputX.setVisible(false);
			inputY.setVisible(false);
			inputMines.setVisible(false);
			boardSetUp = true;
			easy.setVisible(false);
			medium.setVisible(false);
			hard.setVisible(false);
			custom.setVisible(false);
			enter.setVisible(false);
			xText.setVisible(false);
			yText.setVisible(false);
			minesText.setVisible(false);
			timer.setVisible(true);
			mine.setVisible(true);
			counter = 0;
			timerGo = true;
			firstPress = true;
			winState = "none";
			repaint();
			
			//parameters for the size of the board
			x = 16;
			y = 8;
			mines = 25;
			total = x * y;
			
			//place the "Reset Game" button and the "Time: _:__" in the correct position
			resetGame.setBounds(((x * 40)/2) - 70, (y * 40) + 20, 160, 20);
			timer.setBounds(((x * 40)/2) - 20, (y * 40) + 60, 160, 20);
			mine.setBounds(((x * 40)/2) - 18, (y * 40) + 80, 160, 20);
			
			//set up the board
			setUpBoard();
		}
		
		//if it is the hard button
		else if (e.getSource().equals(hard)) {
			resetGame.setVisible(true);
			inputX.setVisible(false);
			inputY.setVisible(false);
			inputMines.setVisible(false);
			boardSetUp = true;
			easy.setVisible(false);
			medium.setVisible(false);
			hard.setVisible(false);
			custom.setVisible(false);
			enter.setVisible(false);
			xText.setVisible(false);
			yText.setVisible(false);
			minesText.setVisible(false);
			timer.setVisible(true);
			mine.setVisible(true);
			counter = 0;
			timerGo = true;
			firstPress = true;
			winState = "none";
			repaint();
			
			//parameters for the size of the board
			x = 32;
			y = 16;
			mines = 100;
			total = x * y;
			
			//place the "Reset Game" button and the "Time: _:__" in the correct position
			resetGame.setBounds(((x * 40)/2) - 70, (y * 40) + 20, 160, 20);
			timer.setBounds(((x * 40)/2) - 20, (y * 40) + 60, 160, 20);
			mine.setBounds(((x * 40)/2) - 18, (y * 40) + 80, 160, 20);
			
			//set up the board
			setUpBoard();
		}
		
		//if it is the custom button
		else if (e.getSource().equals(custom)) {
			resetGame.setVisible(false);
			inputX.setVisible(true);
			inputY.setVisible(true);
			inputMines.setVisible(true);
			boardSetUp = false;
			easy.setVisible(false);
			medium.setVisible(false);
			hard.setVisible(false);
			custom.setVisible(false);
			enter.setVisible(true);
			xText.setVisible(true);
			yText.setVisible(true);
			minesText.setVisible(true);
			timer.setVisible(false);
			mine.setVisible(false);
			firstPress = false;
			winState = "none";
			repaint();
		}
		
		//if it is the enter button
		else if (e.getSource().equals(enter)) {
			resetGame.setVisible(true);
			inputX.setVisible(false);
			inputY.setVisible(false);
			inputMines.setVisible(false);
			boardSetUp = true;
			easy.setVisible(false);
			medium.setVisible(false);
			hard.setVisible(false);
			custom.setVisible(false);
			enter.setVisible(false);
			xText.setVisible(false);
			yText.setVisible(false);
			minesText.setVisible(false);
			timer.setVisible(true);
			mine.setVisible(true);
			counter = 0;
			timerGo = true;
			firstPress = true;
			winState = "none";
			repaint();
			
			x = -1;
			y = -1;
			mines = -1;
			
			boolean times = true;
			String Xsize = inputX.getText(), Ysize = inputY.getText(), minesAmnt = inputMines.getText();
			int i = 0;
			
			//read the input from the text boxes
			while (times) {
				if (Xsize.equals("" + i)) {
					x = i;
				}
				
				if (Ysize.equals("" + i)) {
					y = i;
				}
				
				if (minesAmnt.equals("" + i)) {
					mines = i;
				}
				
				if (x != -1 && y != -1 && mines != -1) {
					times = false;
				}
				
				i++;
			}
			
			total = x * y;
			
			//place the "Reset Game" button, "Mines: __", and the "Time: _:__" in the correct position
			resetGame.setBounds(((x * 40)/2) - 70, (y * 40) + 20, 160, 20);
			timer.setBounds(((x * 40)/2) - 20, (y * 40) + 60, 160, 20);
			mine.setBounds(((x * 40)/2) - 18, (y * 40) + 80, 160, 20);
			
			//set up the board
			setUpBoard();
		}
		
		else if (e.getSource().equals(t)) {
			if (timerGo) {
				timer.setText("Time: " + (counter/600) + ":" + ((counter/100)%6) + (((counter/10)%60)%10));
				counter += 1;
				mine.setText("Mines: " + mines);
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		//get the x and y of the mouse
		int x = (e.getX() - 10) / 40;
	    int y = (e.getY() - 10) / 40;
	    
	    //detect where they clicked if it is on the board screen
	    if (boardSetUp && this.x > x && this.y > y) {
	    	//if it is a right click
	    	if (e.getButton() == 1) {
		    	//detect if in the square has not been flipped/flagged
				if (squares[x][y].getFile().equals("blank.png")) {
	    			//if it is the first press, insure it is a blank space (0 mines)
	    			while (squares[x][y].getMines() != 0 && firstPress) {
	    				//redo the board
	    				setUpBoard();
	    			}
	    			
	    			//reset the first press variable
	    			firstPress = false;
	    			
	    			//reveal the current square
	    			squares[x][y].setFile(reveal(squares[x][y]));
	    			
	    			//array to store adjacent blank squares (x, y) values
	    			int xVals[] = new int[10000], yVals[] = new int[10000], count = 0;
	    			for (int a = 0; a < 100; a++) {
   	    				xVals[a] = -1;
   	    				yVals[a] = -1;
   	    			}
	    			
	    			//look if adjacent blanks need to be flipped
	    			while (zero) {
	    				zero = false;
	    				
	    				//if it is a square in the middle of the board
	    				if (x != 0 && y != 0 && x!= this.x - 1 && y != this.y - 1) {
	    	    			for (int a = -1; a <= 1; a++) {
	    	    				for (int b = -1; b <= 1; b++) {
	    	    					squares[x + a][y + b].setFile(reveal(squares[x + a][y + b]));
	    	    					
	    	    					if (zero) {
	    	    						xVals[count] = x + a;
	    	    						yVals[count] = y + b;
	    	    						count++;
	    	    						zero = false;
	    	    					}
	    	    				}
	    	    			}
	    	   			}
	    	   			
	    				//if it is a square on the left side of the board
	    	   			else if (x == 0) {
	    	   				//if it is the top left corner
	    	   				if (y == 0) {
	    	   					for (int a = 0; a <= 1; a++) {
	    		   					for (int b = 0; b <= 1; b++) {
	    		   						squares[x + a][y + b].setFile(reveal(squares[x + a][y + b]));
	    		   						
	    		   						if (zero) {
		    	    						xVals[count] = x + a;
		    	    						yVals[count] = y + b;
		    	    						count++;
		    	    						zero = false;
		    	    					}
	    		   					}
	    		   				}
	    	   				}
	    	   				
	    	   				//if it is the bottom left corner
	    	   				else if (y == this.y - 1) {
	    	   					for (int a = 0; a <= 1; a++) {
	    	    					for (int b = -1; b <= 0; b++) {
	    	    						squares[x + a][y + b].setFile(reveal(squares[x + a][y + b]));
	    	    						
	    	    						if (zero) {
		    	    						xVals[count] = x + a;
		    	    						yVals[count] = y + b;
		    	    						count++;
		    	    						zero = false;
		    	    					}
	    	    					}
	   		    				}
	   		   				}
	   		  				
	    	   				//if it is anywhere else on the side
    		   				else {
	   		   					for (int a = 0; a <= 1; a++) {
    		    					for (int b = -1; b <= 1; b++) {
    		    						squares[x + a][y + b].setFile(reveal(squares[x + a][y + b]));
    		    						
    		    						if (zero) {
		    	    						xVals[count] = x + a;
		    	    						yVals[count] = y + b;
		    	    						count++;
		    	    						zero = false;
    		    						}
	    		    				}
	    	    				}
	    	   				}
	    	   			}
	    	   			
	    				//if it is a square on the right side of the board
	    	   			else if (x == this.x - 1) {
	    	   				//if it is the top right corner
	    	   				if (y == 0) {
	    	   					for (int a = -1; a <= 0; a++) {
	    		    				for (int b = 0; b <= 1; b++) {
	    		    					squares[x + a][y + b].setFile(reveal(squares[x + a][y + b]));
	    		    					
	    		    					if (zero) {
		    	    						xVals[count] = x + a;
		    	    						yVals[count] = y + b;
		    	    						count++;
		    	    						zero = false;
	    		    					}
	    				    		}
	    		    			}
	    		   			}
	    		   			
	    	   				//if it is the bottom right corner
	    		   			else if (y == this.y - 1) {
	    		   				for (int a = -1; a <= 0; a++) {
	    		   					for (int b = -1; b <= 0; b++) {
	    		   						squares[x + a][y + b].setFile(reveal(squares[x + a][y + b]));
	    		   						
	    		   						if (zero) {
		    	    						xVals[count] = x + a;
		    	    						yVals[count] = y + b;
		    	    						count++;
		    	    						zero = false;
	    		   						}
	    		   					}
	    		   				}
	   						}
	   		    			
	    	   				//if it is anywhere else on the side
	   			    		else {
	   							for (int a = -1; a <= 0; a++) {
	   			   					for (int b = -1; b <= 1; b++) {
	   			   						squares[x + a][y + b].setFile(reveal(squares[x + a][y + b]));
	   			   						
		   			   					if (zero) {
		    	    						xVals[count] = x + a;
		    	    						yVals[count] = y + b;
		    	    						count++;
		    	    						zero = false;
		   			   					}
	   			   					}
	   			   				}
	   						}
	   		    		}
	   		    		
	    				//if it is the top side
	   		    		else if (y == 0) {
	   		    			for (int a = -1; a <= 1; a++) {
	   							for (int b = 0; b <= 1; b++) {
	   								squares[x + a][y + b].setFile(reveal(squares[x + a][y + b]));
	   								
	   								if (zero) {
	    	    						xVals[count] = x + a;
	    	    						yVals[count] = y + b;
	    	    						count++;
	    	    						zero = false;
	   								}
	   							}
	   			    		}
	   		    		}
	   		   			
	    				//if it is the bottom side
	   	 				else if (y == this.y - 1) {
	    					for (int a = -1; a <= 1; a++) {
		    					for (int b = -1; b <= 0; b++) {
		    						squares[x + a][y + b].setFile(reveal(squares[x+ a][y + b]));
 		    						
		    						if (zero) {
		   	    						xVals[count] = x + a;
		   	    						yVals[count] = y + b;
		   	    						count++;
		        						zero = false;
   			   						}
   			    				}
   			    			}
   		    			}
	    					
	    				//if there are more blanks that are not yet evalueated, look at the next one in the loop
		    			if (count > 0) {
			    			zero = true;
			   				count--;
			   				x = xVals[count];
			   				y = yVals[count];
			   				xVals[count] = -1;
		    				yVals[count] = -1;
		    			}
	    			}
		    	}
	    	}
	    	
	    	//if it is a left click
	    	else if (e.getButton() == 3) {
			    //flag it if it is blank
				if (squares[x][y].getFile().equals("blank.png")) {
					total--;
	   				squares[x][y].setFile("flag.png");
	   				mines--;
	   			}
	  			
			    //unflag it if it is flagged
			    else if (squares[x][y].getFile().equals("flag.png")) {
			    	total++;
			   		squares[x][y].setFile("blank.png");
			   		mines++;
			   	}
		    }
	    	
	    	//see if the game has ended
			if (gameOver || (total - mines) == 0) {
				gameOver = false;
				boardSetUp = false;
				
				//see if they hit a mine or if they flagged everything correctly
				for (int k = 0; k < this.x; k++) {
			    	for (int l = 0; l < this.y; l++) {
			    		//if it is a bad flag
			    		if (squares[k][l].getFile().equals("flag.png") && squares[k][l].getMines() != 9) {
			    			squares[k][l].setFile("badFlag.png");
			    			win = false;
			    			winState = "lose";
			    			repaint();
			    		}
			    		
			    		//if it is a good flag
			    		else if (squares[k][l].getFile().equals("flag.png") && squares[k][l].getMines() == 9) {
			    			squares[k][l].setFile("mine.png");
			    			repaint();
			    		}
			    	}
				}
				
				//display win text
				if (win) {
					winState = "win";
					repaint();
					
					//reveal unmarked mines
					for (int k = 0; k < this.x; k++) {
			    		for (int l = 0; l < this.y; l++) {
			    			//if it is an unmarked mine
				    		if (squares[k][l].getFile().equals("blank.png") && squares[k][l].getMines() == 9) {
				    			squares[k][l].setFile("mine.png");
				    			repaint();
				    		}
				    	}
					}
				}
				
				//display loss text
				else {
					winState = "lose";
					repaint();
					win = true;
					
					//explode unmarked mines
					for (int k = 0; k < this.x; k++) {
			    		for (int l = 0; l < this.y; l++) {
			    			//if it is an unmarked mine
				    		if (squares[k][l].getFile().equals("blank.png") && squares[k][l].getMines() == 9) {
				    			squares[k][l].setFile("explodedMine.png");
				    			repaint();
				    		}
				    	}
					}
				}
				
				//stop the timer
				timerGo = false;
			}
		}
	}
	
	//method to reveal a square
	private String reveal(Square x) {
		String s = x.getFile();;
		if (x.getFile().equals("blank.png")) {
			total--;
			switch (x.getMines()) {
				case 0:
					s = "zero.png";
					zero = true;
					break;
				case 1:
					s = "one.png";
					break;
				case 2:
					s = "two.png";
					break;
				case 3:
					s = "three.png";
					break;
				case 4:
					s = "four.png";
					break;
				case 5:
					s = "five.png";
					break;
				case 6:
					s = "six.png";
					break;
				case 7:
					s = "seven.png";
					break;
				case 8:
					s = "eight.png";
					break;
				case 9:
					s = "explodedMine.png";
					gameOver = true;
					win = false;
					break;
			}
		}
		return s;
	}
	
	//method to set up the board
	private void setUpBoard() {
		//set up the board
    	for (int j = 0; j < x; j++) {
    		for (int k = 0; k < y; k++) {
    			squares[j][k].setMines(0);
    			add(squares[j][k]);
	    		squares[j][k].setVisible(true);
	    	}
    	}
    	
    	//place the mines
    	for (int j = 0; j < mines; j++) {
    		int a = (int) (Math.random()*x), b = (int)(Math.random()*y);
    		if (squares[a][b].getMines() != 9) {
    			squares[a][b].setMines(9);
    		}
    		else {
    			j--;
    		}
    	}
    	
    	//place the numbers
    	for (int j = 0; j < x; j++) {
    		for (int k = 0; k < y; k++) {
    			//if it is a square in the middle of the board
    			if (squares[j][k].getMines() != 9) {	    				
    				if (j != 0 && k != 0 && j!= x - 1 && k != y - 1) {
	    				for (int a = -1; a <= 1; a++) {
	    					for (int b = -1; b <= 1; b++) {
	    						if (squares[j + a][k + b].getMines() == 9) 
	    							squares[j][k].setMines(squares[j][k].getMines() + 1);
	    					}
	    				}
    				}
    				
    				//if it is a square on the left side of the board
    				else if (j == 0) {
    					//if it is the top left corner
    					if (k == 0) {
    						for (int a = 0; a <= 1; a++) {
		    					for (int b = 0; b <= 1; b++) {
		    						if (squares[j + a][k + b].getMines() == 9) 
		    							squares[j][k].setMines(squares[j][k].getMines() + 1);
		    					}
		    				}
	    				}
	    				
    					//if it is the bottom left corner
	    				else if (k == y - 1) {
	    					for (int a = 0; a <= 1; a++) {
		    					for (int b = -1; b <= 0; b++) {
		    						if (squares[j + a][k + b].getMines() == 9) 
		    							squares[j][k].setMines(squares[j][k].getMines() + 1);
		    					}
		    				}
	    				}
    					
    					//if it is anywhere else on that side
	    				else {
	    					for (int a = 0; a <= 1; a++) {
		    					for (int b = -1; b <= 1; b++) {
		    						if (squares[j + a][k + b].getMines() == 9) 
		    							squares[j][k].setMines(squares[j][k].getMines() + 1);
		    					}
		    				}
	    				}
    				}
    				
    				//if it is a square on the right side of the board
    				else if (j == x - 1) {
    					//if it is the top right corner
    					if (k == 0) {
    						for (int a = -1; a <= 0; a++) {
		    					for (int b = 0; b <= 1; b++) {
		    						if (squares[j + a][k + b].getMines() == 9) 
		    							squares[j][k].setMines(squares[j][k].getMines() + 1);
		    					}
		    				}
	    				}
	    				
    					//if it is the bottom left corner
	    				else if (k == y - 1) {
	    					for (int a = -1; a <= 0; a++) {
		    					for (int b = -1; b <= 0; b++) {
		    						if (squares[j + a][k + b].getMines() == 9) 
		    							squares[j][k].setMines(squares[j][k].getMines() + 1);
		    					}
		    				}
	    				}
    					
    					//if it anywhere else on that side
	    				else {
	    					for (int a = -1; a <= 0; a++) {
		    					for (int b = -1; b <= 1; b++) {
		    						if (squares[j + a][k + b].getMines() == 9) 
		    							squares[j][k].setMines(squares[j][k].getMines() + 1);
		    					}
		    				}
	    				}
    				}
    				
    				//if it is the top side
    				else if (k == 0) {
    					for (int a = -1; a <= 1; a++) {
	    					for (int b = 0; b <= 1; b++) {
	    						if (squares[j + a][k + b].getMines() == 9) 
	    							squares[j][k].setMines(squares[j][k].getMines() + 1);
	    					}
	    				}
    				}
    				
    				//if it is the bottom side
    				else if (k == y - 1) {
    					for (int a = -1; a <= 1; a++) {
	    					for (int b = -1; b <= 0; b++) {
	    						if (squares[j + a][k + b].getMines() == 9) 
	    							squares[j][k].setMines(squares[j][k].getMines() + 1);
	    					}
	    				}
    				}
    			}
    		}
    	}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//draw the win text
		if (winState.equals("win")) {
			g.setColor(Color.GREEN);
			g.setFont(new Font("TimesRoman", Font.BOLD, 24));
			g.drawString("You Win", ((x * 40)/2) - 35, (y * 40) + 120);
			mines = 0;
			mine.setText("Mines: " + mines);
		}
		
		//draw the loss text
		else if (winState.equals("lose")) {
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.BOLD, 24));
			g.drawString("You Lose", ((x * 40)/2) - 35, (y * 40) + 120);
		}
	}
	//unused methods
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
}