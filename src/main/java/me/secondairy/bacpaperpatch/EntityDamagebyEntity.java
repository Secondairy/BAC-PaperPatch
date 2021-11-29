package me.secondairy.bacpaperpatch;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static me.secondairy.bacpaperpatch.Main.grantAdvancement;

public class EntityDamagebyEntity implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Damageable victim = (Damageable) e.getEntity();
        if (e.getDamager() instanceof Player) {
            if (victim instanceof Drowned) {
                if ((victim.getHealth() - e.getDamage()) <= 0) {
                    ItemStack mainHand = Objects.requireNonNull(((Drowned) victim).getEquipment()).getItemInMainHand();
                    ItemStack offHand = Objects.requireNonNull(((Drowned) victim).getEquipment()).getItemInOffHand();
                    ItemStack trident = new ItemStack(Material.TRIDENT);
                    ItemStack shell = new ItemStack(Material.NAUTILUS_SHELL);
                    if ((mainHand.isSimilar(trident) && offHand.isSimilar(shell))
                            || mainHand.isSimilar(shell) && offHand.isSimilar(trident)) {
                        e.setCancelled(true);
                        grantAdvancement("blazeandcave","monsters/captain_etho", (Player) e.getDamager());
                        victim.damage(e.getDamage());
                    }
                }
            }
        }
    }
}
