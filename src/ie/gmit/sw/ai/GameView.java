package ie.gmit.sw.ai;

/**
 * GameView - Paints the components onto the window
 * @author Ryan Conway
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class GameView extends JPanel implements ActionListener{
	//Initialize sizes for maze/cells
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;	
	private int cellspan = 5;	
	private int cellpadding = 2;
	private Maze maze;
	private Sprite[] sprites;
	private int enemy_state = 5;
	private Timer timer;
	private int currentRow;
	private int currentCol;
	private boolean zoomOut = false;
	private int imageIndex = -1;
	private int offset = 48; //The number 0 is ASCII 48.
	private Color[] reds = {new Color(255,160,122), new Color(139,0,0), new Color(255, 0, 0)}; //Animate enemy "dots" to make them easier to see
	
	public Character character = new Character(1, 1);
	public Weapon weapon = new Weapon(WeaponType.FIST, 5);
	
	//Sets foundations for maze and starts timer
	public GameView(Maze maze) throws Exception{
		this.maze = maze;
		setBackground(Color.LIGHT_GRAY);
		setDoubleBuffered(true);
		timer = new Timer(300, this);
		timer.start();
	}
	
	//Set a row
	public void setCurrentRow(int row) {
		if (row < cellpadding){
			currentRow = cellpadding;
		}else if (row > (maze.size() - 1) - cellpadding){
			currentRow = (maze.size() - 1) - cellpadding;
		}else{
			currentRow = row;
		}
	}

	//Set a Column
	public void setCurrentCol(int col) {
		if (col < cellpadding){
			currentCol = cellpadding;
		}else if (col > (maze.size() - 1) - cellpadding){
			currentCol = (maze.size() - 1) - cellpadding;
		}else{
			currentCol = col;
		}
	}

	//Paints the window 
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
              
        cellspan = zoomOut ? maze.size() : 5;         
        final int size = DEFAULT_VIEW_SIZE/cellspan;
        g2.drawRect(0, 0, GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
        
        for(int row = 0; row < cellspan; row++) {
        	for (int col = 0; col < cellspan; col++){  
        		int x1 = col * size;
        		int y1 = row * size;
        		
        		char ch = '0';
        		
        		//Check to determine what perspective to draw
        		if (zoomOut){
        			ch = maze.get(row, col);
        			if (ch >= '5'){
	        			if (row == currentRow && col == currentCol){
	        				g2.setColor(Color.YELLOW);
	        			}
	        			else{
	        				g2.setColor(reds[(int) (Math.random() * 3)]);
	        			}
        			}
        			if(ch == '\u003E') {
        				g2.setColor(Color.ORANGE);
        			}
        			g2.fillRect(x1, y1, size, size);
        		}else{
        			ch = maze.get(currentRow - cellpadding + row, currentCol - cellpadding + col);
        		}
        		
        		imageIndex = (int) ch;
        		imageIndex -= offset;
        		if (imageIndex < 0){
        			g2.setColor(Color.LIGHT_GRAY);//Empty cell
        			g2.fillRect(x1, y1, size, size);   			
        		}else{
        			g2.drawImage(sprites[imageIndex].getNext(), x1, y1, null);
        		}
        	}
        }
	}
	
	//Toggles the view which determines what should be drawn
	public void toggleZoom(){
		zoomOut = !zoomOut;		
	}

	//Repaints the enemy when an action is performed
	public void actionPerformed(ActionEvent e) {	
		if (enemy_state < 0 || enemy_state == 5){
			enemy_state = 6;
		}else{
			enemy_state = 5;
		}
		this.repaint();
	}
	
	//Set the sprites
	public void setSprites(Sprite[] sprites){
		this.sprites = sprites;
	}
}