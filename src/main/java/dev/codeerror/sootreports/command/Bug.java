package dev.codeerror.sootreports.command;

import dev.codeerror.sootreports.SootReports;
import dev.codeerror.sootreports.util.DiscordWebhook;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.awt.*;

public class Bug extends Command {

    private final TextComponent chatPrefix = SootReports.getInstance().getChatPrefix();

    public Bug() { super("bug"); }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length == 0) {

            if (sender instanceof ProxiedPlayer) {

                ComponentBuilder error = new ComponentBuilder();
                error.append("Too few arguments! Correct Syntax: ").color(ChatColor.RED);
                error.append("/bug <bug>").color(ChatColor.YELLOW);

                sender.sendMessage(chatPrefix, new TextComponent(error.create()));

            }

            return;

        }

        StringBuilder bugBuilder = new StringBuilder();

        for (String word : args) {
            bugBuilder.append(word).append(" ");
        }

        String bug = new String(bugBuilder).trim();

        if (sender instanceof ProxiedPlayer) {

            TextComponent success = new TextComponent();
            success.setText("Bug report has been successfully created and sent to the developer team. Thank you for contributing to the efforts of the developer team.");
            success.setColor(ChatColor.GREEN);

            webhookBug(bug, sender);
            SootReports.getInstance().getLogger().info(sender.getName() + " created a new bug report.");
            sender.sendMessage(chatPrefix, success);

        } else {

            webhookBug(bug, sender);
            SootReports.getInstance().getLogger().info("CONSOLE created a new bug report.");

        }

    }

    @Override
    public boolean hasPermission(CommandSender sender) { return sender.hasPermission("sootreports.command.bug"); }

    private void webhookBug(String bug, CommandSender sender) {

        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();

        embed.setColor(Color.YELLOW);
        embed.setTitle("Bug Report");

        if (sender instanceof ProxiedPlayer) {

            if (((ProxiedPlayer) sender).getServer() != null) {

                embed.addField("Server", "`" + ((ProxiedPlayer) sender).getServer().getInfo().getName() + "`", false);

            } else {

                embed.addField("Server", "`Null`", false);

            }

            embed.addField("Bug", bug, false);
            embed.addField("Reporter", "`" + sender.getName() + "`", false);

        } else {

            embed.addField("Bug", bug, false);
            embed.addField("Reporter", "`CONSOLE`", false);

        }

        SootReports.getInstance().getWebhook().addEmbed(embed);

        try {

            SootReports.getInstance().getWebhook().execute();

        } catch (Exception e) {

            SootReports.getInstance().getLogger().severe("A bug report has failed to send! Ensure that your configuration is correct!");

        }

        SootReports.getInstance().getWebhook().removeEmbed(embed);

    }

}
