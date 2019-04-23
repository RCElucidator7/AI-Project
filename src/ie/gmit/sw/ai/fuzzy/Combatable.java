package ie.gmit.sw.ai.fuzzy;

import ie.gmit.sw.ai.*;
import ie.gmit.sw.ai.Character;
import ie.gmit.sw.ai.enemy.Spider;

public interface Combatable {
	public abstract double combat(Character character, Spider spider, Weapon weapon);
}