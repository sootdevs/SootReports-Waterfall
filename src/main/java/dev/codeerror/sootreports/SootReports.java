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

    private final ConfigurationUtils configUtils = new ConfigurationUtils("config.yml");
    private final Configuration config = configUtils.getConfig();
    private final DiscordWebhook webhook = new DiscordWebhook(config.getString("discord-webhook"));
    private final TextComponent chatPrefix = buildChatPrefix();

    @Override
    public void onEnable() {

        // Define Instance
        instance = this;

        // Configuration
        configUtils.saveDefaultConfig();

        // Commands
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Report());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Bug());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Suggest());
        this.getLogger().info("Successfully registered commands!");

        // Discord Webhook
        webhook.setAvatarUrl("https://cloud.sootmc.com/images/sootreportswebhook.png");
        webhook.setUsername("SootReports");
        this.getLogger().info("Successfully set up Discord Webhook!");

        // Completion
        this.getLogger().info("SootReports v1.0 has been successfully enabled! Created by CodeError.");

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

    public DiscordWebhook getWebhook() { return webhook; }
    private TextComponent buildChatPrefix() {

        ComponentBuilder builder = new ComponentBuilder();
        builder.append("[").color(ChatColor.DARK_GRAY);
        builder.append("SootReports").color(ChatColor.BLUE);
        builder.append("] ").color(ChatColor.DARK_GRAY);
        return new TextComponent(builder.create());

    }
    public TextComponent getChatPrefix() { return chatPrefix; }

}
