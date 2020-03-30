package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.command.commands.scheduler.CommandKothScheduler;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKoth extends VCommand {

	private final CommandKothHelp help;
	
	public CommandKoth() {
		this.setPermission(Permission.ZKOTH_HELP);
		this.addSubCommand(help = new CommandKothHelp());
		this.addSubCommand(new CommandKothCreate());
		this.addSubCommand(new CommandKothNow());
		this.addSubCommand(new CommandKothSpawn());
		this.addSubCommand(new CommandKothVersion());
		this.addSubCommand(new CommandKothSet());
		this.addSubCommand(new CommandKothDelete());
		this.addSubCommand(new CommandKothReload());
		this.addSubCommand(new CommandKothShow());
		this.addSubCommand(new CommandKothStop());
		this.addSubCommand(new CommandKothScheduler());
	}

	@Override
	protected CommandType perform(ZKoth main) {
		help.sender = sender;
		help.perform(main);
		return CommandType.SUCCESS;
	}

}
