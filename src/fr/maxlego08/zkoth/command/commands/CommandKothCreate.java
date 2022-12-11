package fr.maxlego08.zkoth.command.commands;

import java.util.Optional;

import org.bukkit.Location;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.api.Selection;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothCreate extends VCommand {

	public CommandKothCreate(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_CREATE);
		this.addSubCommand("create");
		this.setDescription(Message.DESCRIPTION_CREATE);
		this.setConsoleCanUse(false);
		this.addRequireArg("name");
		this.addOptionalArg("captureSeconds");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		int captureSeconds = argAsInteger(1, 30);

		Optional<Selection> optional = this.manager.getSelection(this.player.getUniqueId());

		if (!optional.isPresent()) {
			message(this.sender, Message.ZKOTH_CREATE_ERROR_SELECTION);
			return CommandType.DEFAULT;
		}

		Selection selection = optional.get();

		if (!selection.isValid()) {
			message(this.sender, Message.ZKOTH_CREATE_ERROR_SELECTION);
			return CommandType.DEFAULT;
		}
		
		if (!selection.isCorrect()) {
			message(this.sender, Message.ZKOTH_CREATE_ERROR_SIZE);
			return CommandType.DEFAULT;
		}

		Location minLocation = selection.getRightLocation();
		Location maxLocation = selection.getLeftLocation();		
		this.manager.createKoth(this.player, name, minLocation, maxLocation, captureSeconds);

		return CommandType.SUCCESS;
	}

}
