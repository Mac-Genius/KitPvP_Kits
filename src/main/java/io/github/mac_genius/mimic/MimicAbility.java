package io.github.mac_genius.mimic;

import me.exellanix.kitpvp.KitPvP;
import me.exellanix.kitpvp.config.KitConfiguration;
import me.exellanix.kitpvp.kit_abilities.Ability;
import me.exellanix.kitpvp.kits.Armor;
import me.exellanix.kitpvp.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mac on 3/8/2016.
 */
public class MimicAbility implements Ability {
    private ArrayList<Action> actions;
    private ItemStack item;
    private KitConfiguration config;

    public MimicAbility() {
        setup();
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public void activateAbility(Player player) {
        int time = (int) getConfig().getSettings().get("time-length");
        int length = (int) getConfig().getSettings().get("reach");
        Player closest = null;
        double distance = 0;
        ArrayList<Entity> nearby = new ArrayList<>(player.getNearbyEntities(length, length, length));
        for (Entity e : nearby) {
            if (e instanceof Player) {
                if (KitPvP.getSingleton().getPlayerKits().containsKey(e)) {
                    if (closest == null) {
                        closest = (Player) e;
                        distance = player.getLocation().distance(e.getLocation());
                    } else {
                        if (player.getLocation().distance(e.getLocation()) < distance) {
                            closest = (Player) e;
                            distance = player.getLocation().distance(e.getLocation());
                        }
                    }
                }
            }
        }
        final Player realClose = closest;
        if (closest != null) {
            Kit current = KitPvP.getSingleton().getPlayerKits().get(player);
            Kit kit = KitPvP.getSingleton().getPlayerKits().get(closest);
            removeItems(player);
            swapInventory(player, kit.getInventory(), kit.getArmor());

            player.sendMessage("You have mimicked " + closest.getName() + " for " + time + " seconds!");
            KitPvP.getSingleton().plugin.getServer().getScheduler().runTaskLater(KitPvP.getSingleton().plugin, () -> {
                if (KitPvP.getSingleton().getPlayerKits().containsKey(player)) {
                    if (KitPvP.getSingleton().getPlayerKits().get(player).equals(current)) {
                        swapInventory(player, current.getInventory(), current.getArmor());
                        player.sendMessage("You have stopped mimicking " + realClose.getName() + "!");
                    }
                }
            }, time * 20);
        } else {
            player.sendMessage("There is no one nearby to mimic!");
        }

    }

    @Override
    public List<Action> getActions() {
        return actions;
    }

    @Override
    public boolean hasAction(Action action) {
        return actions.contains(action);
    }

    @Override
    public String getName() {
        return "MIMIC";
    }

    @Override
    public KitConfiguration getConfig() {
        return config;
    }

    @Override
    public void setConfig(KitConfiguration kitConfiguration) {
        this.config = kitConfiguration;
    }

    private void setup() {
        actions = new ArrayList<>();
        actions.add(Action.RIGHT_CLICK_AIR);
        actions.add(Action.RIGHT_CLICK_BLOCK);

        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta1 = item.getItemMeta();
        meta1.setDisplayName(org.bukkit.ChatColor.BLUE + "" + org.bukkit.ChatColor.BOLD + "Mimic " + org.bukkit.ChatColor.DARK_GRAY + "" + org.bukkit.ChatColor.BOLD + "(Right click!)");
        item.setItemMeta(meta1);
        this.item = item;

        KitConfiguration config = new KitConfiguration(true, "Mimic");
        HashMap<String, Object> values = new HashMap<>();
        values.put("time-length", 30);
        values.put("reach", 7);
        config.saveDefaultSettings(values);
        this.config = config;
    }

    private void swapInventory(Player player, ItemStack[] inventory, Armor armor) {
        ItemStack[] kitInv = inventory;
        armor.resetArmor(player);
        for (int i = 0; i < kitInv.length; i++) {
            if (kitInv[i] != null && kitInv[i].getType() != Material.MUSHROOM_SOUP && kitInv[i].getType() != Material.BOWL) {
                player.getInventory().setItem(i, inventory[i]);
            }
        }
    }

    private void removeItems(Player player) {
        player.getInventory().setItem(0, null);
        player.getInventory().setItem(1, null);
    }
}
