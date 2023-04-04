package dk.banannus.generators.utils;

import net.md_5.bungee.api.ChatColor;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chat {
	public static String colored(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	public static String plain(String s) {
		return s.replaceAll("§", "&");

	}

	public static List<String> getColored(List<String> lore) {
		List<String> coloredLore = new ArrayList<String>();
		for (String line : lore) {
			coloredLore.add(ChatColor.translateAlternateColorCodes('&', line));
		}
		return coloredLore;
	}
	public static String[] getColored(String... stringList) {
		if (stringList == null)
			return null;
		for (int i = 0; i < stringList.length; i++)
			stringList[i] = getColored(stringList[i]);
		return stringList;
	}

	public static String getColored(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

}
