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

public class Suggest extends Command {

    private final TextComponent chatPrefix = SootReports.getInstance().getChatPrefix();

    public Suggest() { super("suggest"); }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length == 0) {

            if (sender instanceof ProxiedPlayer) {

                ComponentBuilder error = new ComponentBuilder();
                error.append("Too few arguments! Correct Syntax: ").color(ChatColor.RED);
                error.append("/suggest <suggestion>").color(ChatColor.YELLOW);

                sender.sendMessage(chatPrefix, new TextComponent(error.create()));

            }

            return;

        }

        StringBuilder suggestionBuilder = new StringBuilder();

        for (String word : args) {
            suggestionBuilder.append(word).append(" ");
        }

        String suggestion = new String(suggestionBuilder).trim();

        if (sender instanceof ProxiedPlayer) {

            TextComponent success = new TextComponent();
            success.setText("Suggestion has been successfully created and sent to the developer team. Thank you for contributing new ideas to the server.");
            success.setColor(ChatColor.GREEN);

            webhookSuggestion(suggestion, sender);
            SootReports.getInstance().getLogger().info(sender.getName() + " created a new suggestion.");
            sender.sendMessage(chatPrefix, success);

        } else {

            webhookSuggestion(suggestion, sender);
            SootReports.getInstance().getLogger().info("CONSOLE created a new suggestion.");

        }

    }

    @Override
    public boolean hasPermission(CommandSender sender) { return sender.hasPermission("sootreports.command.suggest"); }

    private void webhookSuggestion(String suggestion, CommandSender sender) {

        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();

        embed.setColor(Color.GREEN);
        embed.setTitle("Suggestion");

        if (sender instanceof ProxiedPlayer) {

            embed.addField("Suggestion", suggestion, false);
            embed.addField("Reporter", "`" + sender.getName() + "`", false);

        } else {

            embed.addField("Suggestion", suggestion, false);
            embed.addField("Reporter", "`CONSOLE`", false);

        }

        SootReports.getInstance().getWebhook().addEmbed(embed);

        try {

            SootReports.getInstance().getWebhook().execute();

        } catch (Exception e) {

            SootReports.getInstance().getLogger().severe("A suggestion has failed to send! Ensure that your configuration is correct!");

        }

        SootReports.getInstance().getWebhook().removeEmbed(embed);

    }

}
