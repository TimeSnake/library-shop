/*
 * Copyright (C) 2023 timesnake
 */

package de.timesnake.library.shop;

import de.timesnake.basic.bukkit.util.user.User;
import de.timesnake.basic.bukkit.util.user.inventory.ExInventory;
import de.timesnake.library.pets.pets.Pet;

import java.util.List;

public class ShopMenu {

  private final User user;
  private final ExInventory inventory;

  public ShopMenu(ShopManager shopManager, User user) {
    this.user = user;
    this.inventory = new ExInventory(54, "Shop");

    List<Pet<?>> pets = shopManager.getPetManager().getPetsByUser().get(this.user);
    if (!pets.isEmpty()) {
      this.loadSection(10, new PetSettings(shopManager, this.user, pets.getFirst()));
    } else {
      this.loadSection(10, new PetSettings(shopManager, this.user, null));
    }
    this.loadSection(11, new ActionItemShop(shopManager, this.user));
  }

  private void loadSection(int slot, ShopSection section) {
    this.inventory.setItemStack(slot, section.getDisplayItem());
  }

  public void openInventory() {
    this.user.openInventory(this.inventory);
  }
}
