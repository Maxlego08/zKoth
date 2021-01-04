package fr.maxlego08.zkoth.zcore.utils.commands;

import java.util.function.BiConsumer;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;

public class ZCommand extends VCommand {

	private BiConsumer<VCommand, ZKothPlugin> command;

	@Override
	public CommandType perform(ZKothPlugin main) {
		
		if (command != null){
			command.accept(this, main);
		}

		return CommandType.SUCCESS;
	}

	public VCommand setCommand(BiConsumer<VCommand, ZKothPlugin> command) {
		this.command = command;
		return this;
	}

	public VCommand sendHelp(String command) {
		this.command = (cmd, main) -> main.getCommandManager().sendHelp(command, cmd.getSender());
		return this;
	}

}
