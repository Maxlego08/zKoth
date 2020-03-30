package fr.maxlego08.koth.command;

import java.util.function.BiConsumer;

import fr.maxlego08.koth.ZKoth;

public class ZCommand extends VCommand {

	private BiConsumer<VCommand, ZKoth> command;

	@Override
	public CommandType perform(ZKoth main) {
		
		if (command != null){
			command.accept(this, main);
		}

		return CommandType.SUCCESS;
	}

	public VCommand setCommand(BiConsumer<VCommand, ZKoth> command) {
		this.command = command;
		return this;
	}

	public VCommand sendHelp(String command) {
		this.command = (cmd, main) -> main.getCommandManager().sendHelp(command, cmd.getSender());
		return this;
	}

}
