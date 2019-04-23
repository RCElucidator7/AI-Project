package ie.gmit.sw.ai.fuzzy;

/**
 * FuzzyLogic - determines the results of combat through fcl file
 */
import ie.gmit.sw.ai.*;
import ie.gmit.sw.ai.Character;
import ie.gmit.sw.ai.enemy.Spider;
import net.sourceforge.jFuzzyLogic.FIS;
//import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class FuzzyLogic implements Combatable {
	
	public double combat(Character character, Spider spider, Weapon weapon) {
		
		FIS fis = FIS.load("./resources/fuzzy/combat.fcl", true);
		//FunctionBlock fb = fis.getFunctionBlock("combat");
		fis.setVariable("weapon", weapon.getPower());
		fis.setVariable("playerLife", character.getLifeForce());
		fis.setVariable("spriteLife", spider.getLife());
		fis.evaluate();
		
		double crispOutputResult = fis.getVariable("chanceToWin").getValue();
		double damage = (100 - crispOutputResult)/10;

        System.out.println("=======================\nCurrent health: " + character.getLifeForce());
        System.out.println("Damage taken: " + damage + "\n=======================");

		return damage;
	}

}