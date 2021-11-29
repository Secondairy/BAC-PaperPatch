package me.secondairy.bacpaperpatch;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static me.secondairy.bacpaperpatch.Main.grantAdvancement;
import static me.secondairy.bacpaperpatch.Main.checkRequired;

public class EntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();

        if (player != null) {
            //Logic for RPS and Custom Boss Fight
            if (event.getEntityType() == EntityType.ZOMBIE) {
                ItemStack currHand = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
                List<ItemStack> fullDiamond = new ArrayList<ItemStack>() {{
                    add(new ItemStack(Material.DIAMOND_HELMET));
                    add(new ItemStack(Material.DIAMOND_CHESTPLATE));
                    add(new ItemStack(Material.DIAMOND_LEGGINGS));
                    add(new ItemStack(Material.DIAMOND_BOOTS));
                    add(new ItemStack(Material.DIAMOND_SWORD));
                }};

                //Checks the dropped items against the fullDiamond array list
                if (checkRequired(event.getDrops(), fullDiamond, null)) {
                    grantAdvancement("blazeandcave","monsters/custom_boss_fight", player);
                }

                //Checking for shears, paper or cobblestone in the drop
                //Then checking for the reciprocal item in the player's hand
                if ((checkRequired(event.getDrops(), null, new ItemStack(Material.SHEARS)) && Objects.equals(currHand.getType(), Material.COBBLESTONE))
                        || (checkRequired(event.getDrops(), null, new ItemStack(Material.COBBLESTONE)) && Objects.equals(currHand.getType(), Material.PAPER))
                        || (checkRequired(event.getDrops(), null, new ItemStack(Material.PAPER)) && Objects.equals(currHand.getType(), Material.SHEARS))
                ) {
                    grantAdvancement("blazeandcave","weaponry/rock_paper_shears", player);
                }
            }
            //Logic for Mollusc Man
            if (event.getEntityType() == EntityType.DROWNED) {
                if (checkRequired(event.getDrops(), null, new ItemStack(Material.NAUTILUS_SHELL))) {
                    grantAdvancement("blazeandcave","monsters/mollusc_man", player);
                }
            }
        }
    }
}

