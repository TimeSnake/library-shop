/*
 * Copyright (C) 2023 timesnake
 */

package de.timesnake.library.shop;

import de.timesnake.basic.bukkit.util.Server;
import de.timesnake.basic.bukkit.util.user.User;
import de.timesnake.basic.bukkit.util.user.inventory.ExInventory;
import de.timesnake.basic.bukkit.util.user.inventory.ExItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ItemShop implements InventoryHolder {

  private final int invSize = 6 * 9;
  private User user;
  private ExInventory inventory;

  public ItemShop(User user, CategorySelection.ShopCategory shopCategory) {
    this.user = user;

    inventory = Server.createExInventory(invSize, shopCategory.getName());

    for (ExItemStack item : shopCategory.getOffer()) {
      inventory.setItemStack(item);
    }

  }


  @Override
  public Inventory getInventory() {
    return inventory.getInventory();
  }
}
