package dk.banannus.generators.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import dk.banannus.generators.events.custom.events.SellChestPlaceEvent;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class SellChestPlaceEvt extends SkriptEvent {

	static {
		Skript.registerEvent("SellChest Place", SellChestOpenEvt.class, SellChestPlaceEvent.class,  "[on] sell[-]chest place");
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "on sellchest place";
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
