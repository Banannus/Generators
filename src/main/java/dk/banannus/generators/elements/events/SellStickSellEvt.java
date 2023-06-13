package dk.banannus.generators.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import dk.banannus.generators.events.custom.events.GenRemoveEvent;
import dk.banannus.generators.events.custom.events.SellStickSellEvent;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class SellStickSellEvt extends SkriptEvent {

	static {
		Skript.registerEvent("Sell Stick Sell", SellStickSellEvt.class, SellStickSellEvent.class,  "[on] sell[-]stick sell");
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "on sellstick sell";
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
