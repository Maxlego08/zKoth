package fr.maxlego08.koth.command.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKothLoot extends VCommand {

	public CommandKothLoot() {
		this.setDescription(Message.DESCRIPTION_LOOT);
		this.setPermission(Permission.ZKOTH_LOOT);
		this.addSubCommand("loot");
		this.setConsoleCanUse(false);
	}

	@Override
	protected CommandType perform(ZKoth main) {

		Inventory inv = Bukkit.createInventory(null, 27, "§ezKOTH §6Loots");

		List<String> str = (Config.itemstacks == null ? Config.itemstacks = new ArrayList<>() : Config.itemstacks);
		str.stream().map(e -> decode(e)).filter(item -> item != null).forEach(item -> inv.addItem(item));

		player.openInventory(inv);

		return CommandType.SUCCESS;
	}

}
