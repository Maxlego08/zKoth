package fr.maxlego08.zkoth.command.commands;

import java.util.Optional;

import org.bukkit.Location;

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

}
