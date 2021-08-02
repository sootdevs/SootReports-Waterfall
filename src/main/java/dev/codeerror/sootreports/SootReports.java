package dev.codeerror.sootreports;

import dev.codeerror.sootreports.command.Bug;
import dev.codeerror.sootreports.command.Report;
import dev.codeerror.sootreports.command.Suggest;
import dev.codeerror.sootreports.util.ConfigurationUtils;
import dev.codeerror.sootreports.util.DiscordWebhook;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.config.Configuration;

import java.util.ArrayList;
import java.util.List;

public class SootReports extends Plugin implements TabExecutor {

    private static SootReports instance;

    private DiscordWebhook reportsWebhook;
    private DiscordWebhook bugsWebhook;
    private DiscordWebhook suggestionsWebhook;

    private final TextComponent chatPrefix = buildChatPrefix();

    @Override
    public void onEnable() {

        // Define Instance
        instance = this;

        // Configuration
        ConfigurationUtils configUtils = new ConfigurationUtils("config");
        configUtils.saveDefaultConfig();
        Configuration config = configUtils.getConfig();

        // Commands
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Report());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Bug());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Suggest());
        this.getLogger().info("Successfully registered commands!");

        // Discord Webhooks
        reportsWebhook = new DiscordWebhook(config.getString("discord-webhook-reports"));
        bugsWebhook = new DiscordWebhook(config.getString("discord-webhook-bugs"));
        suggestionsWebhook = new DiscordWebhook(config.getString("discord-webhook-suggestions"));

        reportsWebhook.setAvatarUrl("https://cloud.sootmc.com/images/sootreportswebhook.png");
        bugsWebhook.setAvatarUrl("https://cloud.sootmc.com/images/sootreportswebhook.png");
        suggestionsWebhook.setAvatarUrl("https://cloud.sootmc.com/images/sootreportswebhook.png");

        reportsWebhook.setUsername("SootReports");
        bugsWebhook.setUsername("SootReports");
        suggestionsWebhook.setUsername("SootReports");

        reportsWebhook.setContent(":rotating_light:  [**ALERT**]  `New Player Report!`  <@&" + config.getString("reports-role-id") + ">");
        bugsWebhook.setContent(":cloud_lightning:  [**ALERT**]  `New Bug Report!`  <@&" + config.getString("bugs-role-id") + ">");
        this.getLogger().info("Successfully set up Discord Webhooks!");

        // Completion
        this.getLogger().info("SootReports v1.1 has been successfully enabled! Created by CodeError.");

    }

    @Override
    public void onDisable() {

        ProxyServer.getInstance().getPluginManager().unregisterCommands(this);

    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("report")) {

                List<ProxiedPlayer> players = new ArrayList<>(((ProxiedPlayer) sender).getServer().getInfo().getPlayers());
                List<String> playerNames = new ArrayList<>();

                for (ProxiedPlayer player : players) {
                    playerNames.add(player.getName());
                }

                return playerNames;

            }

        }

        return null;

    }

    public static SootReports getInstance() { return instance; }

    public DiscordWebhook getReportsWebhook() { return reportsWebhook; }
    public DiscordWebhook getBugsWebhook() { return bugsWebhook; }
    public DiscordWebhook getSuggestionsWebhook() { return suggestionsWebhook; }

    private TextComponent buildChatPrefix() {

        ComponentBuilder builder = new ComponentBuilder();
        builder.append("[").color(ChatColor.DARK_GRAY);
        builder.append("SootReports").color(ChatColor.BLUE);
        builder.append("] ").color(ChatColor.DARK_GRAY);
        return new TextComponent(builder.create());

    }
    public TextComponent getChatPrefix() { return chatPrefix; }

}
