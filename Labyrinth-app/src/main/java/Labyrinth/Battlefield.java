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
		String message = "";
		switch (abilityP) {
			case HEAL:
				this.player.reduseHp(abilityP.getDamage());
				message += "You heal yourself";
				break;
			case SKIP_TURN:
			case BLOCK:
				message += "Player does not respond";
				break;
			default:
				this.enemy.reduseHp(abilityP.getDamage());
				message += "You deal " + abilityP.getDamage() + " damage";
				break;
		}
		switch (abilityE) {
			case HEAL:
				this.enemy.reduseHp(abilityE.getDamage());
				message += ", Enemy heals itself";
				break;
			case SKIP_TURN:
			case BLOCK:
				message += ", Enemy does not respond";
				break;
			default:
				this.player.reduseHp(abilityE.getDamage());
				message += ", Enemy deal " + abilityE.getDamage() + " damage";
				break;
		}
		return message;
	}

	public boolean isOver() {
		if (this.player.getHp() <= 0 || this.enemy.getHp() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public Enemy getEnemy() {
		return this.enemy;
	}

	/**
	 * true - player win. false - enemy win
	 */
	public boolean winner() {
		if (this.player.getHp() == 0) {
			return false;
		} else {
			return true;
		}
	}
}
