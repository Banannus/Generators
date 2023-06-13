package dk.banannus.generators.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import dk.banannus.generators.events.custom.events.SellChestOpenEvent;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class SellChestOpenEvt extends SkriptEvent {

	static {
		Skript.registerEvent("SellChest Open", SellChestOpenEvt.class, SellChestOpenEvent.class,  "[on] sellchest open");
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "on sellchest open";
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
