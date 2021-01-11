package fr.maxlego08.zkoth.command.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.api.enums.LootType;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSetType extends VCommand {

	public CommandKothSetType() {
		this.setPermission(Permission.ZKOTH_TYPE);
		this.addSubCommand("settype");
		this.setDescription("Set loot type");
		this.addRequireArg("name");
		this.addRequireArg("type");
		this.setTabCompletor();
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		String type = argAsString(1);

		try {
			LootType lootType = LootType.valueOf(type.toUpperCase());
			manager.setKothLoot(sender, name, lootType);
		} catch (Exception e) {
			return CommandType.SYNTAX_ERROR;
		}

		return CommandType.SUCCESS;
	}

	@Override
	public List<String> toTab(ZKothPlugin plugin, CommandSender sender2, String[] args) {

		if (manager == null)
			manager = plugin.getKothManager();
		if (args.length == 2) {
			String startWith = args[1];
			return generateList(manager.getKothNames(), startWith);
		} else if (args.length == 3) {

			String startWith = args[2];
			List<String> strings = Arrays.asList(LootType.values()).stream().map(e -> e.name().toLowerCase())
					.collect(Collectors.toList());
			return this.generateList(strings, startWith);

		}

		return null;
	}

}