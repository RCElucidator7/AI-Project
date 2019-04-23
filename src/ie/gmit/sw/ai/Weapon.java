package ie.gmit.sw.ai;

/**
 * Weapon - determines the type and power of a weapon
 * @author Ryan Conway
 *
 */

public class Weapon {

	private WeaponType weaponType;
	private double power;
	
	public Weapon() {
	}

	public Weapon(WeaponType weaponType, double power) {
		super();
		this.weaponType = weaponType;
		this.power = power;
	}

	public double getPower() {
		return power;
	}
	
	public void setPower(double power) {
		this.power = power;
	}

	public WeaponType getWeaponEnum() {
		return weaponType;
	}

	public void setWeaponEnum(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	public String toString() {
		return "Weapon [weaponType=" + weaponType + ", weaponPower=" + power + "]";
	}
}


