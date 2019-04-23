package ie.gmit.sw.ai;

/**
 * WeaponType - enum that has four values for each type of weapon
 * @author Ryan Conway
 *
 */
public enum WeaponType {

	FIST("FIST"),
	SWORD("SWORD"), 
	BOMB("BOMB"), 
	HYDROGENBOMB("HYDROGENBOMB");
	
	private final String name;
	
	private WeaponType(String name) {
		this.name = name;
	}
	
	
	public String toString(){
		return this.name;
	}
	
}