package Labyrinth;

import Labyrinth.enemy.Enemy;

public class Battlefield {
	private Player player;
	private Enemy enemy;
	private AbilityP abilityP;
	private AbilityE abilityE;

	Battlefield(Player player, Enemy enemy) {
		this.player = player;
		this.enemy = enemy;
	}

	public void setAbility(AbilityP ability) {
		this.abilityP = ability;
		this.abilityE = AbilityE.getRandom();
	}

	/**
	 * stud
	 */
	public String battle() {
		return "in developing";
	}

	public boolean isOver() {
		return true;
	}

	public Enemy getEnemy() {
		return this.enemy;
	}

	/**
	 * true - player win.
	 * false - enemy win
	 */
	public boolean winner() {
		return true;
	}
}
