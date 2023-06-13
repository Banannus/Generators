package dk.banannus.generators.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import dk.banannus.generators.events.custom.events.GenPlaceEvent;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class GenPlaceEvt extends SkriptEvent {


	static {
		Skript.registerEvent("Gen Place", GenPlaceEvt.class, GenPlaceEvent.class,  "[on] gen place");
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "on gen place";
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
