package fr.maxlego08.zkoth.command.commands;

import java.util.Arrays;
import java.util.stream.Collectors;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.api.enums.LootType;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSetLootType extends VCommand {

	public CommandKothSetLootType(ZKothPlugin plugin) {
		super(plugin);
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
		this.addCompletion(1, (a, b) -> Arrays.asList(LootType.values()).stream().map(e -> e.name().toLowerCase())
				.collect(Collectors.toList()));
		this.setPermission(Permission.ZKOTH_LOOT_TYPE);
		this.addSubCommand("setloottype", "setltype");
		this.setDescription(Message.DESCRIPTION_LOOTTYPE);
		this.addRequireArg("type");
		this.setTabCompletor();
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		String type = argAsString(1);

		try {
			LootType lootType = LootType.valueOf(type.toUpperCase());
			this.manager.setKothLoot(sender, name, lootType);
		} catch (Exception e) {
			return CommandType.SYNTAX_ERROR;
		}

		return CommandType.SUCCESS;
	}

}