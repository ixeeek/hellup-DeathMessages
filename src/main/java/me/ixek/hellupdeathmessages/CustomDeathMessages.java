package me.ixek.hellupdeathmessages;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.ixek.hellupdeathmessages.commands.CustomDeathMessagesCommand;
import me.ixek.hellupdeathmessages.enums.VersionEnums;
import me.ixek.hellupdeathmessages.listeners.PlayerDeathListener;
import me.ixek.hellupdeathmessages.listeners.PlayerKilledByEntityListener;
import me.ixek.hellupdeathmessages.listeners.PlayerLoginListener;
import me.ixek.hellupdeathmessages.metrics.Metrics;

public class CustomDeathMessages extends JavaPlugin {

    public HashMap<String, String> deathMessage = new HashMap<String, String>();

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        checkVersion();
        registerStatistics();
        registerCommandsListeners();

        String version = getServer().getClass().getPackage().getName().substring(getServer().getClass().getPackage().getName().lastIndexOf('.') + 1);
        Bukkit.getLogger().info("[CustomDeathMessages] Enabling version support for " + Bukkit.getServer().getVersion() + "; " + version);
    }

    public VersionEnums getServerVersion()
    {
        if (getServer().getVersion().contains("1.8"))
        {
            return VersionEnums.VERSION_18;
        }
        else if (getServer().getVersion().contains("1.9"))
        {
            return VersionEnums.VERSION_19;
        }
        else if (getServer().getVersion().contains("1.10"))
        {
            return VersionEnums.VERSION_110;
        }
        else if (getServer().getVersion().contains("1.11"))
        {
            return VersionEnums.VERSION_111;
        }
        else if (getServer().getVersion().contains("1.12"))
        {
            return VersionEnums.VERSION_112;
        }
        else if (getServer().getVersion().contains("1.13"))
        {
            return VersionEnums.VERSION_113;
        }
        else if (getServer().getVersion().contains("1.14"))
        {
            return VersionEnums.VERSION_114;
        }
        else if (getServer().getVersion().contains("1.15"))
        {
            return VersionEnums.VERSION_115;
        }
        else if (getServer().getVersion().contains("1.16"))
        {
            return VersionEnums.VERSION_116;
        }
        else if (getServer().getVersion().contains("1.17"))
        {
            return VersionEnums.VERSION_117;
        }
        else if (getServer().getVersion().contains("1.18")) //added by *ixek*
        {
            return VersionEnums.VERSION_118; //added by *ixek*
        }
        else if (getServer().getVersion().contains("1.19")) //added by *ixek*
        {
            return VersionEnums.VERSION_119; //added by *ixek*
        }
        //added by ixek functions are propably unsafe, are made for exact server, that i know it would be safe. bye :)
        else return VersionEnums.OTHER_VERSION;
    }

    public void registerStatistics()
    {
        int pluginId = 7287;
        Metrics metrics = new Metrics(this, pluginId);

        metrics.addCustomChart(new Metrics.SimplePie("head_drop_percentage", () -> String.valueOf(getConfig().getDouble("drop-head-percentage"))));
        metrics.addCustomChart(new Metrics.SimplePie("give_killer_speed", () -> getConfig().getBoolean("give-killer-speed") ? "Enabled" : "Disabled" ));
        metrics.addCustomChart(new Metrics.SimplePie("heart_sucker", () -> getConfig().getBoolean("heart-sucker") ? "Enabled" : "Disabled" ));
        metrics.addCustomChart(new Metrics.SimplePie("do_lightning", () -> getConfig().getBoolean("do-lightning") ? "Enabled" : "Disabled" ));
        metrics.addCustomChart(new Metrics.SimplePie("enable_global_messages", () -> getConfig().getBoolean("enable-global-messages") ? "Enabled" : "Disabled" ));
        metrics.addCustomChart(new Metrics.SimplePie("enable_pvp_messages", () -> getConfig().getBoolean("enable-pvp-messages") ? "Enabled" : "Disabled" ));
        metrics.addCustomChart(new Metrics.SimplePie("enable_entity_name_messages", () -> getConfig().getBoolean("enable-custom-name-entity-messages") ? "Enabled" : "Disabled" ));
        metrics.addCustomChart(new Metrics.SimplePie("enable_original_hover_message",() -> getConfig().getBoolean("original-hover-message") ? "Enabled" : "Disabled" ));
        metrics.addCustomChart(new Metrics.SimplePie("enable_item_tooltip_message",() -> getConfig().getBoolean("enable-item-hover") ? "Enabled" : "Disabled" ));
    }

    public void registerCommandsListeners()
    {
        getServer().getPluginManager().registerEvents(new PlayerDeathListener (this), this);
        getServer().getPluginManager().registerEvents(new PlayerKilledByEntityListener (this), this);
        getServer().getPluginManager().registerEvents(new PlayerLoginListener (this), this);
        getCommand("helldm").setExecutor(new CustomDeathMessagesCommand(this));
        getCommand("helldm").setTabCompleter(new CustomDeathMessagesCommand(this));
    }

    public void checkVersion()
    {
        if (getServerVersion() == VersionEnums.OTHER_VERSION)
        {
            getLogger().warning("[CustomDeathMessages] This server version is unsupported by CustomDeathMessages. Please update to version 1.8 or above on your server to use this plugin.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }
}