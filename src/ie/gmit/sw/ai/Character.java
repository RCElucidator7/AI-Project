package ie.gmit.sw.ai;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * Character Class - Stores all the characters info
 * @author Ryan Conway
 *
 */
public class Character {

	// Life force of the character - initialized
	private double lifeForce = 100;
	// Get the current position
	private int currentRow;
	private int currentCol;
	// List of weapons for the character to use
	private List<Weapon> weaponList = new ArrayList<Weapon>();

	// Constructor
	public Character(int currentRow, int currentCol) {
		super();
		getLifeForce();
		this.currentRow = currentRow;
		this.currentCol = currentCol;
	}

	// Returns the Characters LifeForce 
	public Character() {
		getLifeForce();
	}
	
	// Takes the damage difference from the lifeForce after the battle with the Spider
	public void setDamageTaken(double damage){
		lifeForce -= damage;
		if (! (lifeForce > 0) ) {
			JOptionPane.showMessageDialog(null, "You Died - GAME OVER");
			System.exit(0);
		}
	}
	
	// Getters and setters
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

	// Adds a new weapon to the list
	public void add(Weapon weapon) {
		weaponList.add(weapon);
	}
	
	// Uses a weapon when battling a spider
	// Check to see if the Character has a weapon - can't remove fist
	public Weapon useWeapon() {
		Weapon weapon = weaponList.get(weaponList.size() - 1);
		if(weaponList.size() > 1){
			weaponList.remove(weaponList.size() - 1);
		}
		else {
		}
		return  weapon;
	}
	
	// Return the Characters current list of weapons
	public int weaponCount(){
		return weaponList.size();
	}
	
	// Getter for lifeForce
	public double getLifeForce(){
		return lifeForce;
	}
	
	// Checks if the Character is Alive
	public boolean isAlive(){
		return lifeForce > 0;
	}
	
	// General ToString Method
	public String toString() {
		return "Character [lifeForce=" + lifeForce + ", currentRow=" + currentRow + ", currentCol=" + currentCol + ", weaponList=" + weaponList + "]";
	}
}