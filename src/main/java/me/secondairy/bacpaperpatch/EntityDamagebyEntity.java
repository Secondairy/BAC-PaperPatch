package me.secondairy.bacpaperpatch;

import org.bukkit.Material;
import org.bukkit.entity.*;
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
            Player player = (Player) e.getDamager();
            if ((victim.getHealth() - e.getDamage()) <= 0) {
                //Captain etho logic
                if (victim instanceof Drowned) {
                    ItemStack mainHand = Objects.requireNonNull(((Drowned) victim).getEquipment()).getItemInMainHand();
                    ItemStack offHand = Objects.requireNonNull(((Drowned) victim).getEquipment()).getItemInOffHand();
                    ItemStack trident = new ItemStack(Material.TRIDENT);
                    ItemStack shell = new ItemStack(Material.NAUTILUS_SHELL);
                    if ((mainHand.isSimilar(trident) && offHand.isSimilar(shell))
                            || mainHand.isSimilar(shell) && offHand.isSimilar(trident)) {
                        e.setCancelled(true);
                        grantAdvancement("blazeandcave", "monsters/captain_etho", player);
                        victim.damage(e.getDamage());
                    }
                }

                //trick or treat logic
                if (victim instanceof Zombie
                        || victim instanceof Piglin
                        || victim instanceof PiglinBrute
                        || victim instanceof Player
                        || victim instanceof Skeleton
                        || victim instanceof WitherSkeleton
                        || victim instanceof Stray) {
                    ItemStack helmet = Objects.requireNonNull(((LivingEntity) victim).getEquipment()).getHelmet();
                    ItemStack pumpkin = new ItemStack((Material.CARVED_PUMPKIN));
                    ItemStack jack_o_lantern = new ItemStack((Material.JACK_O_LANTERN));
                    assert helmet != null;
                    if ((helmet.isSimilar(pumpkin))) {
                        e.setCancelled(true);
                        grantAdvancement("blazeandcave", "monsters/trick_or_treat", player);
                        victim.damage(e.getDamage());
                    }
                    if ((helmet.isSimilar(jack_o_lantern))) {
                        e.setCancelled(true);
                        grantAdvancement("blazeandcave", "monsters/handsome_jack", player);
                        victim.damage(e.getDamage());
                    }
                }
            }
        }
    }
}

