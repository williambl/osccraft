package com.williambl.osccraft.common.command;

import com.illposed.osc.transport.udp.OSCPortOut;
import com.williambl.osccraft.OSCCraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

public class CommandOSCCraft extends CommandBase {

    String commandName = "osccraft";
    String usage = "/osccraft <connect|disconnect> [host]";

    String[] subCommands = new String[]{"connect", "disconnect"};

    public CommandOSCCraft() {
        super();
    }

    @Override
    public String getName() {
        return commandName;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return usage;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(new TextComponentString(usage));
            return;
        }
        String subCommand = args[0];

        if (subCommand.equals("connect")) {
            if (args.length < 2) {
                sender.sendMessage(new TextComponentString("No host specified."));
                return;
            }
            InetAddress address;
            try {
                address = InetAddress.getByName(args[1]);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                sender.sendMessage(new TextComponentString("Unknown Host."));
                return;
            }

            try {
                OSCCraft.port = new OSCPortOut(address);
            } catch (IOException e) {
                e.printStackTrace();
                sender.sendMessage(new TextComponentString("Unable to connect."));
            }

            sender.sendMessage(new TextComponentString("Connected."));

            return;
        }

        if (subCommand.equals("disconnect")) {
            OSCCraft.port = null;
            return;
        }

    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {

        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, subCommands);
        }

        if (args[0].equals("connect")) {
            return getListOfStringsMatchingLastWord(args, "localhost", "127.0.0.1");
        }

        return Collections.emptyList();
    }
}
