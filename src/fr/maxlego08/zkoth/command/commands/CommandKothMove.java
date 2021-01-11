package fr.maxlego08.zkoth.command.commands;

import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.api.Selection;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothMove extends VCommand {

	public CommandKothMove() {
		this.setPermission(Permission.ZKOTH_MOVE);
		this.addSubCommand("move");
		this.setDescription("Move a koth");
		this.setConsoleCanUse(false);
		this.addRequireArg("name");
		this.setTabCompletor();
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);

		Optional<Selection> optional = manager.getSelection(player.getUniqueId());

		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_CREATE_ERROR_SELECTION);
			return CommandType.DEFAULT;
		}

		Selection selection = optional.get();

		if (!selection.isValid()) {
			message(sender, Message.ZKOTH_CREATE_ERROR_SELECTION);
			return CommandType.DEFAULT;
		}

		Location minLocation = selection.getRightLocation();
		Location maxLocation = selection.getLeftLocation();
		manager.moveKoth(sender, maxLocation, minLocation, name);

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
