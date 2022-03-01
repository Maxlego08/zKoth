package fr.maxlego08.zkoth.command.commands;

import java.util.Arrays;
import java.util.stream.Collectors;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.api.enums.LootType;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSetType extends VCommand {

	public CommandKothSetType(ZKothPlugin plugin) {
		super(plugin);
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
		this.addCompletion(1, (a, b) -> Arrays.asList(LootType.values()).stream().map(e -> e.name().toLowerCase())
				.collect(Collectors.toList()));
		this.setPermission(Permission.ZKOTH_TYPE);
		this.addSubCommand("settype");
		this.setDescription(Message.DESCRIPTION_TYPE);
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

}