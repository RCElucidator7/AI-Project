package ie.gmit.sw.ai.enemy;

/**
 * Spider Class - Stores all the spiders info
 * @author Ryan Conway
 *
 */
import java.util.LinkedList;
import ie.gmit.sw.ai.node.*;

public class Spider {
    
    // Properties
    private int spiderNumber;
    private char spiderType;
    private int currentRow;
    private int currentCol;
    private int life;
    private double health = 50;
    private double rage;
    private double power;
    private double defence;
    private int range;
    private Pathfinder pf = null;
    
    //Null Constructor
    public Spider(){
    	
    }

    //Constructor with values
    public Spider(int spiderNumber, char spiderType, int currentRow, int currentCol) { 
        this.pf = new Pathfinder();
        this.spiderNumber = spiderNumber;
        this.spiderType = spiderType;
        this.currentRow = currentRow;
        this.currentCol = currentCol;
    }

    // Getters/Setters
    public int getSpiderNumber() {
        return spiderNumber;
    }

    public void setSpiderNumber(int spiderNumber) {
        this.spiderNumber = spiderNumber;
    }

    public char getSpiderType() {
        return spiderType;
    }

    public void setSpiderType(char spiderType) {
        this.spiderType = spiderType;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

    public double getLife() {
        return life;
    }

    public void setLife() {
        if(this.health < 30) {
        	this.life = 1;
        }
        else if(this.health < 10) {
        	this.life = 0;
        }
        else {
        	this.life = 2;
        }
    }
    
	public double getRage() {
		return rage;
	}

	public void setRage(double rage) {
		this.rage = rage;
	}

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getDefence() {
        return defence;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    //Find a path
    public void setPathFinderer(LinkedList<Node> pf) {
        this.pf.clearPath();
        for(Node n : pf){
            this.pf.insertNode(n);
        }
    }

    public Pathfinder getPathFinderer(){
        return this.pf;
    }

}
