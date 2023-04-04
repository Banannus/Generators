package dk.banannus.generators.data.file;

import dk.banannus.generators.Generators;
import dk.banannus.generators.utils.Chat;

import org.bukkit.command.CommandSender;

import java.util.*;

public class ConfigManager {

	static HashMap<String, String[]> messages;

	public void loadALl() {
		messages = new HashMap<>();

		for (String path : Generators.configYML.getKeys(true)) {
			if (!Generators.configYML.isConfigurationSection(path)) {
				if(Generators.configYML.getStringList(path) != null && Generators.configYML.isList(path)) {
					List<String> stringList = Chat.getColored(Generators.configYML.getStringList(path));
					messages.put(path, stringList.toArray(new String[0]));
					continue;
				}

				if(Generators.configYML.getString(path) != null) {
					List<String> stringList = Collections.singletonList(Chat.getColored(Generators.configYML.getString(path)));
					messages.put(path, stringList.toArray(new String[0]));
				}
			}
		}

		for (Map.Entry<String, String[]> entry : messages.entrySet()) {
			System.out.print(entry.getKey());
			System.out.print(Arrays.toString(entry.getValue()));
		}
	}

	public static String[] get(String path) {
		if(messages.containsKey(path)){
			return messages.get(path);
		}
		return new String[] { "" };
	}

	public static void send(CommandSender player, String path) {
		player.sendMessage(get(path));
	}

}
