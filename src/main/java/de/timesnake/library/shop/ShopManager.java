/*
 * Copyright (C) 2023 timesnake
 */

package de.timesnake.library.shop;

import de.timesnake.basic.bukkit.util.Server;
import de.timesnake.basic.bukkit.util.user.User;
import de.timesnake.basic.bukkit.util.user.event.AsyncUserJoinEvent;
import de.timesnake.basic.bukkit.util.user.inventory.ExItemStack;
import de.timesnake.library.basic.util.UserMap;
import de.timesnake.library.pets.PetManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopManager implements Listener {

  private final Logger logger = LogManager.getLogger("shop.manager");

  public ExItemStack shopItem = new ExItemStack(Material.ENDER_CHEST)
      .setDisplayName("§dShop")
      .setLore("§fClick to open the shop")
      .setMoveable(false)
      .setDropable(false)
      .immutable()
      .onInteract(e -> this.shopByUser.get(e.getUser()).openInventory(), true);

  private final PetManager petManager;
  private final int slot;

  private final UserMap<User, ShopMenu> shopByUser = new UserMap<>();

  public ShopManager(JavaPlugin plugin, PetManager petManager, int slot) {
    this.petManager = petManager;
    this.slot = slot;
    Server.registerListener(this, plugin);
  }

  public void loadShopForUser(User user, boolean addItem) {
    ShopMenu menu = new ShopMenu(this, user);
    this.shopByUser.put(user, menu);
    this.logger.info("Loaded shop for user {}", user.getName());
    if (addItem) {
      user.setItem(this.slot, this.shopItem);
    }
  }

  public PetManager getPetManager() {
    return petManager;
  }

  @EventHandler
  public void onUserJoin(AsyncUserJoinEvent event) {
    this.loadShopForUser(event.getUser(), true);
  }
}
