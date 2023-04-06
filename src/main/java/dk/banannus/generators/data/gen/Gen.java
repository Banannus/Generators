package dk.banannus.generators.data.gen;

import java.util.HashMap;

public class Gen {
	private final String key;
	private final String block;
	private final String name;
	private final String drop;
	private final int salgspris;
	private final double xp;
	private final int upgradepris;


	public Gen(String key, String block, String name, String drop, int salgspris, double xp, int upgradepris) {
		this.block = block;
		this.name = name;
		this.drop = drop;
		this.salgspris = salgspris;
		this.xp = xp;
		this.upgradepris = upgradepris;
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public String getBlock() {
		return block;
	}

	public String getName() {
		return name;
	}

	public String getDrop() {
		return drop;
	}

	public int getSalgspris() {
		return salgspris;
	}

	public double getXp() {
		return xp;
	}

	public int getUpgradepris() {
		return upgradepris;
	}
}
