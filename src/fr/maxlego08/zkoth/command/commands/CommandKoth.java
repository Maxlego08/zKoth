package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.command.commands.scheduler.CommandKothScheduler;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKoth extends VCommand {

	public CommandKoth(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_USE);

		this.addSubCommand(new CommandKothCreate(plugin));
		this.addSubCommand(new CommandKothDelete(plugin));
		this.addSubCommand(new CommandKothMove(plugin));
		this.addSubCommand(new CommandKothNow(plugin));
		this.addSubCommand(new CommandKothSpawn(plugin));
		this.addSubCommand(new CommandKothStop(plugin));
		this.addSubCommand(new CommandKothReload(plugin));
		this.addSubCommand(new CommandKothList(plugin));
		this.addSubCommand(new CommandKothInfo(plugin));
		this.addSubCommand(new CommandKothAdd(plugin));
		this.addSubCommand(new CommandKothRemove(plugin));
		this.addSubCommand(new CommandKothSetType(plugin));
		this.addSubCommand(new CommandKothLoot(plugin));
		this.addSubCommand(new CommandKothSetCapture(plugin));
		this.addSubCommand(new CommandKothAxe(plugin));
		this.addSubCommand(new CommandKothVersion(plugin));
		this.addSubCommand(new CommandKothScheduler(plugin));
	}

	@Override
	protected CommandType perform(ZKothPlugin main) {

		message(this.sender, Message.COMMAND_HELP_HEADER);
		this.subVCommands.forEach(command -> {
			messageWO(this.sender, Message.COMMAND_SYNTAXE_HELP, "%command%", command.getSyntax(), "%description%",
					command.getDescription());
		});

		return CommandType.SUCCESS;
	}

}
