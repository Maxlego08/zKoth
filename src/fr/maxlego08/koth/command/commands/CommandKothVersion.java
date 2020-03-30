package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;

public class CommandKothVersion extends VCommand {

	public CommandKothVersion() {
		this.addSubCommand("version");
		this.addSubCommand("v");
		this.addSubCommand("ver");
	}

	@Override
	protected CommandType perform(ZKoth main) {

		message(sender, "§eVersion du plugin§7: §a" + main.getDescription().getVersion());
		message(sender, "§eAuteur§7: §aMaxlego08");
		message(sender, "§eDiscord§7: §ahttps://discord.gg/p9Mdste");
		String user = "%%__USER__%%";
		message(sender, "§eUser account§7: §ahttps://www.spigotmc.org/members/" + user);

		return CommandType.SUCCESS;
	}

}
