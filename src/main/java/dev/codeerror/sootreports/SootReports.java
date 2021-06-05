package dev.codeerror.sootreports;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.config.Configuration;

import dev.codeerror.sootreports.command.Bug;
import dev.codeerror.sootreports.util.DiscordWebhook;
import dev.codeerror.sootreports.command.Report;
import dev.codeerror.sootreports.command.Suggest;

public class SootReports extends Plugin implements TabExecutor {

    private final Configuration config = new Configuration();
    private final DiscordWebhook webhook = new DiscordWebhook(config.getString("discord-webhook-url"));

    @Override
    public void onEnable() {

        this.getLogger().info("Setting up Discord Webhook...");
        webhook.setAvatarUrl("https://cloud.sootmc.com/images/sootreportswebhook.png");
        webhook.setUsername("SootReports");
        this.getLogger().info("Successfully set up Discord Webhook!");

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Report());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Bug());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Suggest());

    }

    @Override
    public void onDisable() {

        ProxyServer.getInstance().getPluginManager().unregisterCommands(this);

    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("report")) {

                List<ProxiedPlayer> players = new ArrayList<>(ProxyServer.getInstance().getServersCopy().get(((ProxiedPlayer) sender).getServer().toString()).getPlayers());
                List<String> playerNames = new ArrayList<>();

                for (ProxiedPlayer player : players) {
                    playerNames.add(player.getName());
                }

                return playerNames;

            }

        }

        return null;

    }

    public DiscordWebhook getWebhook() { return webhook; }

}
