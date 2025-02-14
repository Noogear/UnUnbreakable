package cn.unUnbreakable;

import cn.unUnbreakable.Listeners.BlockBreakPacketListener;
import cn.unUnbreakable.Managers.BlockManager;
import cn.unUnbreakable.Managers.PlayerDigging;
import cn.unUnbreakable.Utils.XLogger;
import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private BlockManager blockManager;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
        PacketEvents.getAPI().getEventManager().registerListener(new BlockBreakPacketListener(this));
    }

    @Override
    public void onEnable() {
        new XLogger(this);
        PacketEvents.getAPI().init();
        blockManager = new BlockManager(this);
        PluginCommand mainCommand = getCommand("ununbreakable");
        if (mainCommand != null) {
            mainCommand.setExecutor(new Commands(this));
        } else {
            XLogger.err("Failed to load command.");
        }
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }


    public BlockManager getBlockManager(){
        return blockManager;
    }

}
