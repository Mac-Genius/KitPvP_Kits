package io.github.mac_genius.mimic;

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
 * Created by Mac on 3/8/2016.
 */
public class Mimic extends ExternalKit {

    @Override
    public void enable() {
        KitPvP.getSingleton().getAbilityManager().registerAbility(new MimicAbility());
        registerKit();
    }

    private void registerKit() {
        ItemStack[] inv = new ItemStack[36];

        for (int i = 2; i < 36; i++) {
            inv[i] = new ItemStack(Material.MUSHROOM_SOUP, 1);
        }

        ItemStack sword = new ItemStack(Material.GOLD_SWORD);
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        chestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        leggings.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

        ItemMeta meta1 = item.getItemMeta();
        meta1.setDisplayName(org.bukkit.ChatColor.BLUE + "" + org.bukkit.ChatColor.BOLD + "Mimic " + org.bukkit.ChatColor.DARK_GRAY + "" + org.bukkit.ChatColor.BOLD + "(Right click!)");
        item.setItemMeta(meta1);

        LeatherArmorMeta im = (LeatherArmorMeta) helmet.getItemMeta();
        im.setColor(Color.BLACK);
        helmet.setItemMeta(im);

        LeatherArmorMeta im1 = (LeatherArmorMeta) chestplate.getItemMeta();
        im1.setColor(Color.BLACK);
        chestplate.setItemMeta(im1);

        LeatherArmorMeta im2 = (LeatherArmorMeta) leggings.getItemMeta();
        im2.setColor(Color.BLACK);
        leggings.setItemMeta(im2);

        LeatherArmorMeta im3 = (LeatherArmorMeta) boots.getItemMeta();
        im3.setColor(Color.BLACK);
        boots.setItemMeta(im3);

        Armor armor = new Armor(helmet, chestplate, leggings, boots);

        ArrayList<Ability> abilities = new ArrayList<>();
        Ability mimic = KitPvP.getSingleton().getAbilityManager().getAbility("MIMIC");
        if (mimic != null) {
            abilities.add(mimic);
        }
        Ability soup = KitPvP.getSingleton().getAbilityManager().getAbility("SOUPREGEN");
        if (soup != null) {
            abilities.add(soup);
        }

        ArrayList<ItemStack> weapons = new ArrayList<>();
        weapons.add(sword);
        inv[0] = sword;
        inv[1] = item;

        String name = ChatColor.BLUE + "" + ChatColor.BOLD + "Mimicker";

        ItemStack icon = AlterItem.nameItem(Material.BLAZE_ROD,
                ChatColor.BLUE + "" + ChatColor.BOLD + "Mimicker");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Steal the powers of other kits by");
        lore.add(ChatColor.GRAY + "mimicking them, then take out your enemies!!");
        lore.add("");
        lore.add(ChatColor.GRAY + "Kit Info:");
        lore.add(ChatColor.GRAY + "  - Right click the blaze rod to mimic the");
        lore.add(ChatColor.GRAY + "    closest enemy within a 7 block radius.");
        AlterItem.addLore(icon, lore);

        ArrayList<String> nameAlias = new ArrayList<>();
        nameAlias.add("MIMIC");
        nameAlias.add("MIMICKER");

        BasicKit kit = new BasicKit(weapons, abilities, armor, name, icon, false, 20, 10000);
        kit.setInventory(inv);
        kit.setKitAlias(nameAlias);

        KitPvP.getSingleton().getKitManager().registerKit(kit);
    }
}
