package dk.banannus.generators.Data;

public class Gen {
	private final String block;
	private final String name;
	private final String drop;
	private final int salgspris;
	private final double xp;
	private final int upgradepris;

	public Gen(String block, String name, String drop, int salgspris, double xp, int upgradepris) {
		this.block = block;
		this.name = name;
		this.drop = drop;
		this.salgspris = salgspris;
		this.xp = xp;
		this.upgradepris = upgradepris;
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
