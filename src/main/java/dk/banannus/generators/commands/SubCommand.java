package dk.banannus.generators.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

	public abstract String getName();

	public abstract String getSyntax();

	public abstract String getDescription();

	public abstract  void perform(Player player, String args[]);

}
