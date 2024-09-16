/*
 * Copyright (C) 2023 timesnake
 */

package de.timesnake.library.shop;

import de.timesnake.basic.bukkit.util.user.User;
import de.timesnake.basic.bukkit.util.user.inventory.ExInventory;
import de.timesnake.basic.bukkit.util.user.inventory.ExItemStack;

public class ShopSection {

  protected final ShopManager shopManager;

  protected final ExInventory inventory;
  protected final ExItemStack displayItem;

  protected final User user;

  public ShopSection(ShopManager shopManager, ExItemStack displayItem, ExInventory inventory, User user) {
    this.shopManager = shopManager;
    this.displayItem = displayItem;
    this.inventory = inventory;
    this.user = user;

    this.displayItem.onClick(e -> this.user.openInventory(this.inventory), true);
  }

  public ExItemStack getDisplayItem() {
    return displayItem;
  }

  public ExInventory getInventory() {
    return inventory;
  }
}
