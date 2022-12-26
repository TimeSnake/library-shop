/*
 * Copyright (C) 2022 timesnake
 */

package de.timesnake.library.shop;

import de.timesnake.basic.bukkit.util.Server;
import de.timesnake.basic.bukkit.util.chat.Plugin;
import de.timesnake.basic.bukkit.util.user.ExItemStack;
import de.timesnake.basic.bukkit.util.user.event.UserInventoryInteractEvent;
import de.timesnake.basic.bukkit.util.user.event.UserInventoryInteractListener;
import de.timesnake.library.basic.util.chat.ExTextColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopManager {

    public static ExItemStack SHOPSELECTION = new ExItemStack(3, Material.ENDER_CHEST, "§dShop")
            .setMoveable(false).setDropable(false).setLore("§fClick to open the shop");

    private final JavaPlugin plugin;
    private final CategorySelection cs;

    public ShopManager(JavaPlugin plugin) {
        this.plugin = plugin;
        Server.printText(Plugin.INFO, "Shop loaded", "Shop");
        cs = new CategorySelection();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

}
