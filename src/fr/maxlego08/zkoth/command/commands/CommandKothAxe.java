package fr.maxlego08.zkoth.command.commands;

import org.bukkit.inventory.ItemStack;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothAxe extends VCommand {

	public CommandKothAxe(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_AXE);
		this.addSubCommand("axe", "wand");
		this.setDescription(Message.DESCRIPTION_AXE);
		this.setConsoleCanUse(false);
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		ItemStack itemStack = this.manager.getKothAxe();
		this.player.getInventory().addItem(itemStack);
		message(this.sender, Message.ZKOTH_AXE_RECEIVE);
		
		return CommandType.SUCCESS;
	}

}
