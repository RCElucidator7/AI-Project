package ie.gmit.sw.ai;

/**
 * GameRunner - sets up the maze and everything in it.
 * @author Ryan Conway
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ie.gmit.sw.ai.enemy.ControllersPool;
public class GameRunner implements KeyListener{
	
	//Constants - Used for maze size and sprite count
	private static final int MAZE_DIMENSION = 100;
	private static final int IMAGE_COUNT = 15;
	
	//Initalize player, view window and maze
	private ControlledSprite player;
	private GameView view;
	private Maze model;
	
	//Current Row/Column - Characters Location
	private int currentRow;
	private int currentCol;
	
	//Pool controller for character maze and spiders
	private ControllersPool controller;
	
	//Character and Weapon types
	private Character character;
	private Weapon weapon;
		
	//Runs the game
	public GameRunner() throws Exception{
		model = new Maze(MAZE_DIMENSION);
    	view = new GameView(model);
    	
    	//Initalize Character and give him a weapon
    	character = new Character(currentRow, currentCol);
    	weapon = new Weapon(WeaponType.FIST, 5);
    	character.add(weapon);
    	
    	//Put the character and maze into the pool controller
    	this.controller = new ControllersPool(model, character);
    	
    	//Set the sprites
    	Sprite[] sprites = getSprites();
    	view.setSprites(sprites);
    	
    	//Place the player in the maze randomly
    	placePlayer();
    	
    	Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
    	view.setPreferredSize(d);
    	view.setMinimumSize(d);
    	view.setMaximumSize(d);
    	
    	JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(this);
        f.getContentPane().setLayout(new FlowLayout());
        f.add(view);
        f.setSize(1000,1000);
        f.setLocation(100,100);
        f.pack();
        f.setVisible(true);
        
        //Run the controller
        this.controller.runController();
	}
	
	//Places the player randomly in the maze
	private void placePlayer(){   	
    	currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	model.set(currentRow, currentCol, '5'); //Player is at index 5
    	updateView(); 		
	}
	
	//Updates the maze and character each frame
	private void updateView(){
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
		
		character.setCurrentRow(currentRow);
		character.setCurrentCol(currentCol);
	}

	//Determines if the user has pressed a key
    public void keyPressed(KeyEvent e) {
    	//Checks if the user press Right arrow/D and moves them in that direction
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D && currentCol < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow, currentCol + 1)){
				player.setDirection(Direction.RIGHT);
				currentCol++; 
        	}
        //Checks if the user press Left arrow/A and moves them in that direction	
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A && currentCol > 0) {
        	if (isValidMove(currentRow, currentCol - 1)) {
				player.setDirection(Direction.LEFT);
				currentCol--;	
			}
    	//Checks if the user press Up arrow/W and moves them in that direction
        }else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W  && currentRow > 0) {
        	if (isValidMove(currentRow - 1, currentCol)) {
				player.setDirection(Direction.UP);
				currentRow--;
			}
    	//Checks if the user press Down arrow/S and moves them in that direction
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S  && currentRow < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow + 1, currentCol)){
        		player.setDirection(Direction.DOWN);
				currentRow++;
        	}         	  	
    	//Checks if the user has pressed Z and toggles the zoom of the maze
        }else if (e.getKeyCode() == KeyEvent.VK_Z){
        	view.toggleZoom();
        }
        //Checks if the user has pressed E and displays the characters current lifeForce and Weapon
        else if (e.getKeyCode() == KeyEvent.VK_E){
        	JOptionPane.showMessageDialog(null, "Current Health : " + character.getLifeForce() + "\nCurrent Weapon: " + weapon.getWeaponEnum());
        
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){ //Checks if the user presses escape and exits the window
        	System.exit(0);
        }else{
        	return;
        }
        
        updateView();
    }
    public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore
   
	//Checks if the character can make a valid move
	//This may consist of picking up a weapon, picking up a help item or moving into an available space
	private boolean isValidMove(int row, int col){
		
		//Variables for each item type
		char sword = '\u0031', help = '\u0032', bomb = '\u0033', hydrogenBomb = '\u0034', goal = '\u003E';
    	
		//Checks to see if the player is able to pick up a sword and adds it to their inventory
    	if (row <= model.size() - 1 && col <= model.size() -1 && model.get(row, col) == sword) { 
    		weapon = new Weapon(WeaponType.SWORD, 15); 
    		System.out.println("Picked up Sword!");
    		model.set(row, col, '0'); 
    		character.add(weapon); 
    	}
    	//Checks to see if the player is able to pick up a bomb and adds it to their inventory
    	if (row <= model.size() - 1 && col <= model.size() -1 && model.get(row, col) == bomb) { 
    		weapon = new Weapon(WeaponType.BOMB, 25); 
    		System.out.println("Picked up Bomb");
    		model.set(row, col, '0'); 
    		character.add(weapon); 
    	}
    	//Checks to see if the player is able to pick up a hydrogen and adds it to their inventory
    	if (row <= model.size() - 1 && col <= model.size() -1 && model.get(row, col) == hydrogenBomb) { 
    		weapon = new Weapon(WeaponType.HYDROGENBOMB, 50); 
    		System.out.println("Picked up Hydrogen Bomb");
    		model.set(row, col, '0'); 
    		character.add(weapon); 
    	}
    	//Checks to see if the player is able to use the help item. The co-ordinates of the goal is displayed on screen
    	if (row <= model.size() - 1 && col <= model.size() -1 && model.get(row, col) == help) { 
    		JOptionPane.showMessageDialog(null, model.getGoal());
    		model.set(row, col, '0');
    	}
    	//Checks to see if the player is at the target. Triggers You Win pop up and closes the window
    	if (row <= model.size() - 1 && col <= model.size() -1 && model.get(row, col) == goal) { 
    		JOptionPane.showMessageDialog(null, "Congratulations! You Win!");
    		System.exit(0);
    	}
		//Checks if the player can move into a free space
		if (row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col) == ' '){
			model.set(currentRow, currentCol, '\u0020');
			model.set(row, col, '5');
			return true;
		}else{
			return false; //Can't move
		}
	}
	
	private Sprite[] getSprites() throws Exception{
		//Read in the images from the resources directory as sprites. Note that each
		//sprite will be referenced by its index in the array, e.g. a 3 implies a Bomb...
		//Ideally, the array should dynamically created from the images... 
		
		//ExecutorPool pool = new FixedSizeThreadPool();
		
		player = new ControlledSprite("Main Player", 3, "resources/images/player/d1.png", "resources/images/player/d2.png", "resources/images/player/d3.png", "resources/images/player/l1.png", "resources/images/player/l2.png", "resources/images/player/l3.png", "resources/images/player/r1.png", "resources/images/player/r2.png", "resources/images/player/r3.png");
		
		Sprite[] sprites = new Sprite[IMAGE_COUNT];
		sprites[0] = new Sprite("Hedge", 1, "resources/images/objects/hedge.png");
		sprites[1] = new Sprite("Sword", 1, "resources/images/objects/sword.png");
		sprites[2] = new Sprite("Help", 1, "resources/images/objects/help.png");
		sprites[3] = new Sprite("Bomb", 1, "resources/images/objects/bomb.png");
		sprites[4] = new Sprite("Hydrogen Bomb", 1, "resources/images/objects/h_bomb.png");
		sprites[5] = player;
		sprites[6] = new Sprite("Black Spider", 2, "resources/images/spiders/black_spider_1.png", "resources/images/spiders/black_spider_2.png");
		sprites[7] = new Sprite("Blue Spider", 2, "resources/images/spiders/blue_spider_1.png", "resources/images/spiders/blue_spider_2.png");
		sprites[8] = new Sprite("Brown Spider", 2, "resources/images/spiders/brown_spider_1.png", "resources/images/spiders/brown_spider_2.png");
		sprites[9] = new Sprite("Green Spider", 2, "resources/images/spiders/green_spider_1.png", "resources/images/spiders/green_spider_2.png");
		sprites[10] = new Sprite("Grey Spider", 2, "resources/images/spiders/grey_spider_1.png", "resources/images/spiders/grey_spider_2.png");
		sprites[11] = new Sprite("Orange Spider", 2, "resources/images/spiders/orange_spider_1.png", "resources/images/spiders/orange_spider_2.png");
		sprites[12] = new Sprite("Red Spider", 2, "resources/images/spiders/red_spider_1.png", "resources/images/spiders/red_spider_2.png");
		sprites[13] = new Sprite("Yellow Spider", 2, "resources/images/spiders/yellow_spider_1.png", "resources/images/spiders/yellow_spider_2.png");
		sprites[14] = new Sprite("Goal", 1, "resources/images/objects/goal.png");
		return sprites;
		
	}
	
	public static void main(String[] args) throws Exception{
		new GameRunner();
	}
}