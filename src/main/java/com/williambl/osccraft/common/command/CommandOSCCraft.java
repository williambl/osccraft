package com.williambl.osccraft.common.command;

import com.illposed.osc.OSCMessage;
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
import java.util.Optional;

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

        if (subCommand.equals("send")) {
            List<Object> msgArgs = new java.util.ArrayList<>(Collections.emptyList());
            String address = "/";
            for (int i = 1; i < args.length; i++) {
                String arg = args[i];
                if (i == 1)
                    address = arg;
                else {
                    Optional<Float> argFloat = new Optional<>();
                    Optional<Integer> argInt = new Optional<>();
                    Optional<Boolean> argBool = new Optional<>();

                    try {
                        argFloat = Optional.of(Float.parseFloat(arg));
                        argInt = Optional.of(Integer.parseInt(arg));
                    } catch (NumberFormatException e) {
                        if (arg.equalsIgnoreCase("true"))
                            argBool = Optional.of(true);
                        else if (arg.equalsIgnoreCase("false"))
                            argBool = Optional.of(false);
                    }

                    if (argFloat.isPresent())
                        msgArgs.add(argFloat.get());
                    else if (argInt.isPresent())
                        msgArgs.add(argInt.get());
                    else if (argBool.isPresent())
                        msgArgs.add(argBool.get());
                    else
                        msgArgs.add(arg);
                }
            }

            OSCMessage msg = new OSCMessage(address, msgArgs);

            try {
                OSCCraft.port.send(msg);
            } catch (Exception e) {
                sender.sendMessage(new TextComponentString("Failed to send message"));
            }
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
