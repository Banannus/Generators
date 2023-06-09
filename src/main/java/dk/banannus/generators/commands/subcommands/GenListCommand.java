package dk.banannus.generators.commands.subcommands;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import dk.banannus.generators.commands.SubCommand;
import dk.banannus.generators.data.file.ConfigManager;
import dk.banannus.generators.data.gen.Gen;
import dk.banannus.generators.data.gen.GensManager;
import dk.banannus.generators.utils.Chat;
import dk.banannus.generators.utils.Config;
import dk.banannus.generators.utils.GUI;
import dk.banannus.generators.utils.GlassColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class GenListCommand extends SubCommand {


	@Override
	public String getName() {
		return "list";
	}

	@Override
	public String getSyntax() {
		return "&e/gen list";
	}

	@Override
	public String getDescription() {
		return "&7Se listen af generators.";
	}

	@Override
	public void perform(Player player, String[] args) {
		if(args[0].equalsIgnoreCase("list")) {
			if(GensManager.getGenList().isEmpty()) {
				ConfigManager.send(player, "messages.no-gens-in-list");
				return;
			}

			PaginatedGui paginatedGui = Gui.paginated()
					.title(Component.text(ConfigManager.get("messages.prefix")[0]))
					.rows(6)
					.pageSize(36)
					.disableAllInteractions()
					.create();

			String topRow = ConfigManager.get("GUI.top-row")[0];
			String bottomRow = ConfigManager.get("GUI.bottom-row")[0];

			GuiItem topBottomRow = ItemBuilder.from(GUI.createItemGlass(Material.STAINED_GLASS_PANE, GlassColor.getGlassColor(Chat.plain(topRow)), "&f")).name(Component.text(Chat.colored("&7"))).asGuiItem();
			GuiItem bottomRowItem = ItemBuilder.from(GUI.createItemGlass(Material.STAINED_GLASS_PANE, GlassColor.getGlassColor(Chat.plain(bottomRow)), "&f")).name(Component.text(Chat.colored("&7"))).asGuiItem();

			for(int i = 0; i < 9; i++) {
				paginatedGui.setItem(i, topBottomRow);
			}

			for(int i = 45; i < 54; i++) {
				paginatedGui.setItem(i, bottomRowItem);
			}

			paginatedGui.setItem(6, 3, ItemBuilder.from(Material.PAPER).name(Component.text(Chat.colored("&7Tilbage"))).asGuiItem(event -> paginatedGui.previous()));
			paginatedGui.setItem(6, 7, ItemBuilder.from(Material.PAPER).name(Component.text(Chat.colored("&7Frem"))).asGuiItem(event -> paginatedGui.next()));

			paginatedGui.setItem(1, 4, ItemBuilder.from(Material.ENDER_CHEST).name(Component.text(ConfigManager.get("sell-chest.name")[0])).asGuiItem(event -> {
				ItemStack chest = setDisplayName(new ItemStack(Material.ENDER_CHEST), ConfigManager.get("sell-chest.name")[0]);
				player.getInventory().addItem(chest);
			}));
			paginatedGui.setItem(1, 6, ItemBuilder.from(Material.STICK).name(Component.text(ConfigManager.get("numbers.sell-stick-name")[0])).asGuiItem(event -> {
				ItemStack stick = setDisplayName(new ItemStack(Material.STICK), ConfigManager.get("numbers.sell-stick-name")[0]);
				player.getInventory().addItem(stick);
			}));


			int i = 8;
			for(Map.Entry<String, Gen> entry : GensManager.getGenList().entrySet()) {
				List<Component> componentList = new ArrayList<>();
				Gen gen = entry.getValue();
				String[] lores = ConfigManager.get("GUI.lore-pr-item");
				for (String lore : lores) {
					String replacedLore = lore
							.replace("%drop%", gen.getDropName())
							.replace("%salgspris%", String.valueOf(gen.getSalgspris()))
							.replace("%xp%", String.valueOf(gen.getXp()))
							.replace("%upgrade%", String.valueOf(gen.getUpgradepris()));
					componentList.add(Component.text(Chat.colored(replacedLore)));
				}

				GuiItem genItem = ItemBuilder.from(GensManager.getBlockItemStack(gen.getKey())).name(Component.text(Chat.colored(gen.getName()))).lore(componentList).asGuiItem(event ->{
					if(!player.hasPermission(ConfigManager.get("admin.staff-permission")[0])) return;
					ItemStack genBlock = GensManager.getBlockItemStack(gen.getKey());
					ItemMeta meta = genBlock.getItemMeta();
					meta.setDisplayName(Chat.colored(gen.getName()));
					genBlock.setItemMeta(meta);
					player.getInventory().addItem(genBlock);
				});
				paginatedGui.setItem(Integer.parseInt(gen.getKey()) , genItem);
			}
			paginatedGui.open(player);
		}
	}
	public static ItemStack setDisplayName(ItemStack block, String displayName) {
		ItemMeta meta = block.getItemMeta();
		meta.setDisplayName(displayName);
		block.setItemMeta(meta);
		return block;
	}
}
