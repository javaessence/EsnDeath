package esn.essence.death.esndeath;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.file.FileConfiguration;


public final class EsnDeath extends JavaPlugin implements Listener {

    private Sound Sound;
    private String Title;
    private String Subtitle;
    private int TitleFadeIn;
    private int TitleStay;
    private int TitleFadeOut;
    private boolean EnableTitle;


    @Override
    public void onEnable() {
        getLogger().info("§aвключён.");
        Bukkit.getPluginManager().registerEvents(this, this);

        saveDefaultConfig();
        reloadConfig();
        FileConfiguration config = getConfig();
        Sound = Sound.valueOf(config.getString("Sound"));
        Title = config.getString("Title");
        Subtitle = config.getString("Subtitle");
        TitleFadeIn = config.getInt("TitleFadeIn");
        TitleStay = config.getInt("TitleStay");
        TitleFadeOut = config.getInt("TitleFadeOut");
        EnableTitle = config.getBoolean("EnableTitle");


        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        getLogger().info("§cотключён.");
        // Plugin shutdown logic
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (EnableTitle) {
            sendTitle(player, Title, Subtitle, TitleFadeIn, TitleStay, TitleFadeOut);
        }

        playRespawnSound(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
            }
        }.runTaskLater(this, 1L);
    }


    private void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    private void playRespawnSound(Player player) {
        player.playSound(player.getLocation(), Sound, 1.0f, 1.0f);
    }
}
