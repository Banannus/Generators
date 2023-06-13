package dk.banannus.generators.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import dk.banannus.generators.events.custom.events.GenUpgradeEvent;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class GenUpgradeEvt extends SkriptEvent {

	static {
		Skript.registerEvent("Gen Upgrade", GenUpgradeEvt.class, GenUpgradeEvent.class,  "[on] gen upgrade");
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "on gen upgrade";
	}

	@Override
	public boolean check(Event e) {
		return true;
	}

	@Override
	public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parser) {
		return true;
	}
}
