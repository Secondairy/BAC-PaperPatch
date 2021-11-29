package me.secondairy.bacpaperpatch;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public static final Logger logger = Logger.getLogger("BAC-PaperPatch");

    @Override
    public void onEnable() {
        logger.info("BlazeandCaves Paper Patch has been enabled.");
        getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        getServer().getPluginManager().registerEvents(new EntityDamagebyEntity(), this);
    }

    @Override
    public void onDisable() {
        logger.info("BlazeandCaves Paper Patch has been disabled.");
    }

    //Combines namespace and target advancement, then awards the criteria
    //(technically not perfect, but works for this purpose)
    public static void grantAdvancement(String namespace, String advancement, Player player) {
        NamespacedKey key = NamespacedKey.fromString(namespace + ":" + advancement);
        assert key != null;
        AdvancementProgress progress = player.getAdvancementProgress(Objects.requireNonNull(Bukkit.getAdvancement(key)));
        for (String criteria : progress.getRemainingCriteria())
            progress.awardCriteria(criteria);
    }

    //Ignores the Durability of items dropped, preferable to just .containsAll()
    public static boolean checkRequired(List<ItemStack> drops, List<ItemStack> requiredList, ItemStack requiredSingle) {
        List<ItemStack> dropStack = new ArrayList<>();
        drops.forEach((e) -> dropStack.add(new ItemStack(e.getType())));
        if (requiredList == null) {
            return dropStack.contains(requiredSingle);
        } else {
            return dropStack.containsAll(requiredList);
        }
    }
}
