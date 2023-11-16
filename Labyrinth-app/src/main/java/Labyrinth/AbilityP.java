package Labyrinth;


public enum AbilityP {
	PUNCH("/punch", 2), //
	FIRE_BALL("/fire_ball", 18), //
	HEAL("/heal", -4), //
	BLOCK("/block", 0), //
	SKIP_TURN("/skip_turn", 0); //

	private final String command;
	private final int damage;

	AbilityP(String command, int damage) {
		this.command = command;
		this.damage = damage;
	}


	public int getDamage() {
		return damage;
	}

	public static AbilityP getAbility(String input) {
		AbilityP[] abils = values();
		int i = 0;
		while (!abils[i].getCommand().equals(input)) {
			i += 1;
		}
		return abils[i];
	}

	public String getCommand() {
		return command;
	}
}
