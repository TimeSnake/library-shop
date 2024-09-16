/*
 * Copyright (C) 2023 timesnake
 */

package de.timesnake.library.shop;

import de.timesnake.basic.bukkit.util.Server;
import de.timesnake.basic.bukkit.util.chat.Plugin;
import de.timesnake.basic.bukkit.util.chat.cmd.Sender;
import de.timesnake.basic.bukkit.util.user.User;
import de.timesnake.basic.bukkit.util.user.inventory.ExInventory;
import de.timesnake.basic.bukkit.util.user.inventory.ExItemStack;
import de.timesnake.library.pets.pets.Pet;
import org.bukkit.Material;

public class PetSettings extends ShopSection {

  private static final int NAME_PRICE = 100;

  private final ShopManager shopManager;

  private Pet<?> pet;

  public PetSettings(ShopManager shopManager, User user, Pet<?> pet) {
    super(shopManager, new ExItemStack(Material.LEAD)
            .setDisplayName("§9Pet Settings")
            .setLore("", "§7Enable/Disable", "§7Change name"),
        new ExInventory(9, "Pet Settings"),
        user);
    this.shopManager = shopManager;
    this.pet = pet;

    ExItemStack enable = new ExItemStack(Material.GREEN_WOOL)
        .setDisplayName("§9Enable")
        .setLore("", this.pet.isEnabled() ? "§aEnabled" : "§cDisabled")
        .enchant(this.pet.isEnabled())
        .onClick(e -> {
          this.pet.setEnabled(!this.pet.isEnabled());
          e.getClickedItem()
              .enchant(this.pet.isEnabled())
              .setLore("", this.pet.isEnabled() ? "§aEnabled" : "§cDisabled");
        }, true, true);
    this.inventory.setItemStack(enable);

    ExItemStack allowForeignRiding = new ExItemStack(Material.SADDLE)
        .setDisplayName("§9Allow Others to Ride")
        .setLore("", this.pet.isAllowForeignRiding() ? "§aEnabled" : "§cDisabled")
        .enchant(this.pet.isAllowForeignRiding())
        .onClick(e -> {
          this.pet.setAllowForeignRiding(!this.pet.isAllowForeignRiding());
          e.getClickedItem()
              .enchant(this.pet.isAllowForeignRiding())
              .setLore("", this.pet.isAllowForeignRiding() ? "§aEnabled" : "§cDisabled");
          ;
        }, true, true);
    this.inventory.setItemStack(allowForeignRiding);

    ExItemStack name = new ExItemStack(Material.NAME_TAG)
        .setDisplayName("§9Name")
        .setLore("", "§7Update name of pet", "", "§6" + NAME_PRICE + " " + Server.getCoinsName())
        .onClick(e -> this.handleNameUpdate(), true);
    this.inventory.setItemStack(name);
  }

  private void handleNameUpdate() {
    this.user.closeInventory();

    if (user.getCoins() < NAME_PRICE) {
      this.user.sendPluginTDMessage(Plugin.INFO, "Too few coins");
      return;
    }

    this.user.sendPluginTDMessage(Plugin.INFO, "§sType name in chat (use \"&\" for colors):");

    Server.getUserEventManager().addUserChatCommand(this.user, e -> {
      User user = e.getUser();
      String name = e.getMessage().replace('&', '§');
      Sender sender = user.asSender(Plugin.INFO);

      sender.sendPluginTDMessage("§sUpdated name of pet to §v" + name);
      PetSettings.this.pet.setName(name);
      user.removeCoins(NAME_PRICE, true);

      e.setCancelled(true);
    });
  }
}
