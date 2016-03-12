package io.github.mac_genius.blockstrike;

import me.exellanix.kitpvp.KitPvP;
import me.exellanix.kitpvp.Util.AlterItem;
import me.exellanix.kitpvp.external_jars.ExternalKit;
import me.exellanix.kitpvp.kit_abilities.Ability;
import me.exellanix.kitpvp.kits.Armor;
import me.exellanix.kitpvp.kits.BasicKit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;

/**
 * Created by Mac on 3/11/2016.
 */
public class BlockStrike extends ExternalKit {

    @Override
    public void enable() {
        KitPvP.getSingleton().getAbilityManager().registerAbility(new BlockStrikeAbility());
        registerKit();
    }

    private void registerKit() {
        ItemStack[] inv = new ItemStack[36];

        for (int i = 2; i < 36; i++) {
            inv[i] = new ItemStack(Material.MUSHROOM_SOUP, 1);
        }

        ItemStack sword = new ItemStack(Material.GOLD_SWORD);
        ItemStack item = new ItemStack(Material.STICK);
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET);
        ItemStack chestplate = new ItemStack(Material.GOLD_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS);
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);

        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        chestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        leggings.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

        ItemMeta meta1 = item.getItemMeta();
        meta1.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "BlockStrike " + ChatColor.GRAY + "" + ChatColor.BOLD + "(Right click!)");
        item.setItemMeta(meta1);

        Armor armor = new Armor(helmet, chestplate, leggings, boots);

        ArrayList<Ability> abilities = new ArrayList<>();
        Ability soup = KitPvP.getSingleton().getAbilityManager().getAbility("SOUPREGEN");
        if (soup != null) {
            abilities.add(soup);
        }
        ArrayList<ItemStack> weapons = new ArrayList<>();
        weapons.add(sword);
        inv[0] = sword;
        inv[1] = item;

        String name = ChatColor.BLUE + "" + ChatColor.BOLD + "BlockStriker";

        ItemStack icon = AlterItem.nameItem(Material.BLAZE_ROD,
                ChatColor.BLUE + "" + ChatColor.BOLD + "BlockStriker");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Strike your enemies with the force of");
        lore.add(ChatColor.GRAY + "the earth, and kill them while they are dazed!");
        lore.add("");
        lore.add(ChatColor.GRAY + "Kit Info:");
        lore.add(ChatColor.GRAY + "  - Right click the stick to hit players");
        lore.add(ChatColor.GRAY + "    with blocks from the ground.");
        lore.add(ChatColor.GRAY + "  - Max distance is 10 blocks.");
        AlterItem.addLore(icon, lore);

        ArrayList<String> nameAlias = new ArrayList<>();
        nameAlias.add("BLOCKSTRIKE");
        nameAlias.add("BSTRIKE");

        BasicKit kit = new BasicKit(weapons, abilities, armor, name, icon, false, 20, 5000);
        kit.setInventory(inv);
        kit.setKitAlias(nameAlias);

        KitPvP.getSingleton().getKitManager().registerKit(kit);
    }
}
