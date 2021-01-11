package fr.maxlego08.zkoth.command.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothNow extends VCommand {

	public CommandKothNow() {
		this.setPermission(Permission.ZKOTH_SPAWN);
		this.addSubCommand("now");
		this.setDescription("Spawn a koth without cooldown");
		this.addRequireArg("name");
		this.setTabCompletor();
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		manager.spawnKoth(sender, name, true);

		return CommandType.SUCCESS;
	}

	@Override
	public List<String> toTab(ZKothPlugin plugin, CommandSender sender2, String[] args) {
		if (manager == null)
			manager = plugin.getKothManager();
		if (args.length == 2) {
			String startWith = args[1];
			return generateList(manager.getKothNames(), startWith);
		}
		return null;
	}
	
}
