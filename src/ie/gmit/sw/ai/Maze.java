package ie.gmit.sw.ai;

/**
 * Maze - Set up the maze and everything in it
 * @author Ryan Conway
 */

import ie.gmit.sw.ai.enemy.SpiderProduction;
import ie.gmit.sw.ai.enemy.Spider;

public class Maze {
	private char[][] maze; //An array does not lend itself to the type of maze generation a logs we use in the labs. There are no "walls" to carve...
	//Pool of spiders
	private SpiderProduction spiders;
	//Public variable to the runner can access where the goal is
	public String goal;
	int spiderNumber = 0;
	
	public Maze(int dimension){
		//Build the maze
		maze = new char[dimension][dimension];
		init();
		buildMaze();
		
		//Generate Spiders
		this.spiders = new SpiderProduction();
		
		//Add features to the maze
		int featureNumber = (int)((dimension * dimension) * 0.01); //Change this value to control the number of objects
		addFeature('\u0031', '0', featureNumber); //1 is a sword, 0 is a hedge
		addFeature('\u0032', '0', featureNumber); //2 is help, 0 is a hedge
		addFeature('\u0033', '0', featureNumber); //3 is a bomb, 0 is a hedge
		addFeature('\u0034', '0', featureNumber); //4 is a hydrogen bomb, 0 is a hedge
		
		featureNumber = (int)((dimension * dimension) * 0.001); //Change this value to control the number of spiders
		addFeature('\u0036', '0', featureNumber); //6 is a Black Spider, 0 is a hedge
		addFeature('\u0037', '0', featureNumber); //7 is a Blue Spider, 0 is a hedge
		addFeature('\u0038', '0', featureNumber); //8 is a Brown Spider, 0 is a hedge
		addFeature('\u0039', '0', featureNumber); //9 is a Green Spider, 0 is a hedge
		addFeature('\u003A', '0', featureNumber); //: is a Grey Spider, 0 is a hedge
		addFeature('\u003B', '0', featureNumber); //; is a Orange Spider, 0 is a hedge
		addFeature('\u003C', '0', featureNumber); //< is a Red Spider, 0 is a hedge
		addFeature('\u003D', '0', featureNumber); //= is a Yellow Spider, 0 is a hedge
		addFeature('\u003E', '0', featureNumber); 
	}
	
	//Initialize the maze
	private void init(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				maze[row][col] = '0'; //Index 0 is a hedge...
			}
		}
	}
	
	//Adds a feature to the maze
	private void addFeature(char feature, char replace, int number){
		int counter = 0;
		while (counter < feature){ //Keep looping until feature number of items have been added
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col] == replace){
				maze[row][col] = feature;
				
				//Add only one Goal to the maze
				if(feature == '\u003E') {
					goal = "Location of goal node:\n Row - " + row + " Col - " + col;
					return;
				 }
				
				//If adding a spider sprite give each a spider type and add to the list of spiders
                if(feature > '\u0035' && feature <= '\u003D'){
                    spiderNumber++;
                    Spider s = new Spider(spiderNumber, feature, row, col);
                    switch (feature){
                        case '\u0036': // Black
                        	generateSpider(s, 2, 0, 2, 0);
                            break;
                        case '\u0037': // Blue
                        	generateSpider(s, 1, 0, 2, 0);
                            break;
                        case '\u0038': // Brown
                        	generateSpider(s, 1, 1, 1, 0);
                            break;
                        case '\u0039': // Green
                        	generateSpider(s, 0, 0, 2, 0);
                            break;
                        case '\u003A': // Grey
                        	generateSpider(s, 0, 2, 0, 0);
                            break;
                        case '\u003B': // Orange
                        	generateSpider(s, 2, 1, 0, 0);
                            break;
                        case '\u003C': // Red
                        	generateSpider(s, 2, 2, 0, 0);
                            break;
                        case '\u003D': // Yellow
                        	generateSpider(s, 0, 1, 1, 0);
                            break;
                        default:
                        	generateSpider(s, 2, 2, 0, 0);
                            break;
                    }
                    spiders.addSpider(s);
                }
				counter++;
			}
		}
		
	}
	
	//Builds maze
	private void buildMaze(){ 
		for (int row = 1; row < maze.length - 1; row++){
			for (int col = 1; col < maze[row].length - 1; col++){
				int num = (int) (Math.random() * 10);
				if (isRoom(row, col)) continue;
				if (num > 5 && col + 1 < maze[row].length - 1){
					maze[row][col + 1] = '\u0020'; //\u0020 = 0x20 = 32 (base 10) = SPACE
				}else {
					if (row + 1 < maze.length - 1) maze[row + 1][col] = '\u0020';
				}
			}
		}	
	}
	
	//Assigns values to the spider and returns that spider
	private Spider generateSpider(Spider s, int rage, int power, int def, int range) {
		s.setLife();
		s.setRage(rage);
        s.setPower(power);
        s.setDefence(def);
        s.setRange(range);
        
        return s;
	}
	
	private boolean isRoom(int row, int col){ //Flaky and only works half the time, but reduces the number of rooms
		return row > 1 && maze[row - 1][col] == '\u0020' && maze[row - 1][col + 1] == '\u0020';
	}
	
	public char[][] getMaze(){
		return this.maze;
	}
	
	public char get(int row, int col){
		return this.maze[row][col];
	}
	
	public void set(int row, int col, char c){
		this.maze[row][col] = c;
	}
	
	public int size(){
		return this.maze.length;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				sb.append(maze[row][col]);
				if (col < maze[row].length - 1) sb.append(",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	//Returns list of spiders
    public SpiderProduction getSpiders() {
        return this.spiders;
    }
    
    //Gets the goal - accessible from the runner
    public String getGoal() {
    	return goal;
    }
}