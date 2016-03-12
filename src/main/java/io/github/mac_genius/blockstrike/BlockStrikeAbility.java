package io.github.mac_genius.blockstrike;

import me.exellanix.kitpvp.KitPvP;
import me.exellanix.kitpvp.config.KitConfiguration;
import me.exellanix.kitpvp.kit_abilities.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mac on 3/11/2016.
 */
public class BlockStrikeAbility implements Ability {
    private ItemStack icon;
    private List<Action> actions;
    private KitConfiguration config;

    public BlockStrikeAbility() {
        setup();
    }

    @Override
    public ItemStack getItem() {
        return icon;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void activateAbility(Player player) {
        Vector v = player.getLocation().getDirection();
        v.setY(0);
        Location start = player.getLocation().getBlock().getLocation();
        start.add(v);
        Vector up = new Vector(0,1,0);
        for (int i = 0; i < (int) config.getSettings().get("reach"); i++) {
            KitPvP.getSingleton().getServer().getScheduler().runTaskLater(KitPvP.getSingleton(), () -> {
                FallingBlock block = player.getWorld().spawnFallingBlock(start, Material.STONE, (byte)0);
                block.setDropItem(false);
                block.setVelocity(up);
            }, i);
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
        return "BLOCKSTRIKE";
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

        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta1 = item.getItemMeta();
        meta1.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "BlockStrike " + ChatColor.GRAY + "" + ChatColor.BOLD + "(Right click!)");
        item.setItemMeta(meta1);
        this.icon = item;

        KitConfiguration config = new KitConfiguration(true, "BlockStrike");
        HashMap<String, Object> values = new HashMap<>();
        values.put("reach", 10);
        config.saveDefaultSettings(values);
        this.config = config;
    }
}
