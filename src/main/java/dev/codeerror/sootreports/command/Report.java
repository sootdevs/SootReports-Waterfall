package dev.codeerror.sootreports.command;

import dev.codeerror.sootreports.SootReports;
import dev.codeerror.sootreports.util.DiscordWebhook;
import dev.codeerror.sootreports.util.TydiumCraftAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.awt.*;

public class Report extends Command {

    private final TextComponent chatPrefix = SootReports.getInstance().getChatPrefix();

    public Report() { super("report"); }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length <= 1) {

            if (sender instanceof ProxiedPlayer) {

                ComponentBuilder error = new ComponentBuilder();
                error.append("Too few arguments! Correct Syntax: ").color(ChatColor.RED);
                error.append("/report <player> <reason>").color(ChatColor.YELLOW);

                sender.sendMessage(chatPrefix, new TextComponent(error.create()));

            }

            return;

        }

        ProxiedPlayer target;

        if (ProxyServer.getInstance().getPlayer(args[0]) != null) {

            target = SootReports.getInstance().getProxy().getPlayer(args[0]);

        } else {

            if (sender instanceof ProxiedPlayer) {

                TextComponent error = new TextComponent();
                error.setText("Player not found! Please ensure the player is online and your spelling is correct!");
                error.setColor(ChatColor.RED);

                sender.sendMessage(chatPrefix, error);

            }

            return;

        }

        StringBuilder reasonBuilder = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            reasonBuilder.append(args[i]).append(" ");
        }

        String reason = new String(reasonBuilder).trim();

        if (sender instanceof ProxiedPlayer) {

            TextComponent success = new TextComponent();
            success.setText("Player report has been successfully created and sent to the staff team. Thank you for contributing to keeping the community safe!");
            success.setColor(ChatColor.GREEN);

            webhookReportPlayer(target, reason, sender);
            SootReports.getInstance().getLogger().info(sender.getName() + " created a new player report.");
            sender.sendMessage(chatPrefix, success);

        } else {

            webhookReportPlayer(target, reason, sender);
            SootReports.getInstance().getLogger().info("CONSOLE created a new player report.");

        }

    }

    @Override
    public boolean hasPermission(CommandSender sender) { return sender.hasPermission("sootreports.command.report"); }

    private void webhookReportPlayer(ProxiedPlayer target, String reason, CommandSender sender) {

        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();

        embed.setColor(Color.RED);
        embed.setTitle("Player Report");

        if (sender instanceof ProxiedPlayer) {

            if (((ProxiedPlayer) sender).getServer() != null) {

                embed.addField("Server", "`" + ((ProxiedPlayer) sender).getServer().getInfo().getName() + "`", false);

            } else {

                embed.addField("Server", "`Null`", false);

            }

            embed.addField("Accused", "`" + target.getName() + "`", false);
            embed.addField("Reason", "`" + reason + "`", false);
            embed.addField("Reporter", "`" + sender.getName() + "`", false);

        } else {

            embed.addField("Server", "`" + target.getServer().getInfo().getName() + "`", false);
            embed.addField("Accused", "`" + target.getName() + "`", false);
            embed.addField("Reason", "`" + reason + "`", false);
            embed.addField("Reporter", "`CONSOLE`", false);

        }

        embed.setThumbnail(new TydiumCraftAPI(target.getUniqueId().toString()).getSkinURL("body", null, "right"));

        SootReports.getInstance().getReportsWebhook().addEmbed(embed);

        try {

            SootReports.getInstance().getReportsWebhook().execute();

        } catch (Exception e) {

            SootReports.getInstance().getLogger().severe("A player report has failed to send! Ensure that your configuration is correct!");

        }

        SootReports.getInstance().getReportsWebhook().removeEmbed(embed);

    }

}
