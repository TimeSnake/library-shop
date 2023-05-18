/*
 * Copyright (C) 2023 timesnake
 */

package de.timesnake.library.shop;

import de.timesnake.basic.bukkit.util.user.inventory.ExItemStack;
import de.timesnake.library.basic.util.Loggers;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopManager {

  public static ExItemStack SHOPSELECTION = new ExItemStack(3, Material.ENDER_CHEST, "§dShop")
      .setMoveable(false).setDropable(false).setLore("§fClick to open the shop");

  private final JavaPlugin plugin;
  private final CategorySelection cs;

  public ShopManager(JavaPlugin plugin) {
    this.plugin = plugin;
    Loggers.SHOP.info("Shop loaded");
    cs = new CategorySelection();
  }

  public JavaPlugin getPlugin() {
    return plugin;
  }

}
