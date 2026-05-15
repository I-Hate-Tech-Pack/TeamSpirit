package cn.hwoxu.teamspirit;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TeamSpirit extends JavaPlugin {
    public static final String cmd = "teamspirit";

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (getCommand(cmd) != null) {
            Objects.requireNonNull(getCommand(cmd)).setExecutor(TeamCommand.TeamCommandInstance);
        }
        getLogger().info("TeamSpirit Command has been enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("TeamSpirit Command has been disabled");
    }
}
