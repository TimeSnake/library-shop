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
import de.timesnake.library.pets.PetType;
import de.timesnake.library.pets.pets.Pet;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

public class PetSettings extends ShopSection {

  private static final int NAME_PRICE = 100;

  private final ShopManager shopManager;

  private Pet<?> pet;

  public PetSettings(ShopManager shopManager, User user, @Nullable Pet<?> pet) {
    super(shopManager, new ExItemStack(Material.LEAD)
            .setDisplayName("§9Pet Settings")
            .setLore("", "§7Toggle pet", "§7Change pet", "§7Change name"),
        new ExInventory(6 * 9, "Pet Menu"),
        user);
    this.shopManager = shopManager;
    this.pet = pet;

    this.loadPetSettings();
    this.loadPets();
  }

  private void loadPetSettings() {
    if (this.pet != null) {
      this.inventory.setItemStack(1, new ExItemStack(Material.GREEN_WOOL)
          .setDisplayName("§9Enable")
          .setLore("", this.pet.isEnabled() ? "§aEnabled" : "§cDisabled")
          .enchant(this.pet.isEnabled())
          .onClick(e -> {
            if (this.pet == null) return;
            this.pet.setEnabled(!this.pet.isEnabled());
            e.getClickedItem()
                .enchant(this.pet.isEnabled())
                .setLore("", this.pet.isEnabled() ? "§aEnabled" : "§cDisabled");
          }, true, true));

      this.inventory.setItemStack(2, new ExItemStack(Material.SADDLE)
          .setDisplayName("§9Allow Others to Ride")
          .setLore("", this.pet.isAllowForeignRiding() ? "§aEnabled" : "§cDisabled")
          .enchant(this.pet.isAllowForeignRiding())
          .onClick(e -> {
            if (this.pet == null) return;
            this.pet.setAllowForeignRiding(!this.pet.isAllowForeignRiding());
            e.getClickedItem()
                .enchant(this.pet.isAllowForeignRiding())
                .setLore("", this.pet.isAllowForeignRiding() ? "§aEnabled" : "§cDisabled");
            ;
          }, true, true));

      this.inventory.setItemStack(3, new ExItemStack(Material.NAME_TAG)
          .setDisplayName("§9Name")
          .setLore("", "§7Update name of pet", "", "§6" + NAME_PRICE + " " + Server.getCoinsName())
          .onClick(e -> {
            if (this.pet == null) return;
            this.handleNameUpdate();
          }, true));
    } else {
      this.inventory.clear(1);
      this.inventory.clear(2);
      this.inventory.clear(3);
    }
  }

  private void loadPets() {
    Collection<PetType> userPetTypes = this.getUserPets().stream().map(Pet::getType).toList();

    int slot = 19;
    for (PetType type : PetType.values()) {
      if (slot % 9 == 8) slot += 2;
      this.inventory.setItemStack(slot, type.getDisplayItem().cloneWithId()
          .setDisplayName(type.getDisplayName())
          .enchant(userPetTypes.contains(type))
          .onClick(e -> {
            Optional<Pet<?>> petOpt = this.getUserPets().stream().filter(pet -> pet.getType() == type).findAny();
            if (petOpt.isPresent()) {
              this.pet = petOpt.get();
              this.user.sendPluginTDMessage(Plugin.INFO, "§sLoaded pet §v" + this.pet.getType().getDisplayName());
              this.loadPetSettings();
              return;
            }
            this.buyPet(type);
          }, true, true));
      slot++;
    }
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

  private boolean buyPet(PetType type) {
    this.user.sendPluginTDMessage(Plugin.INFO, "§wThis feature currently not enabled");
    return false;

    //if (this.user.getCoins() < type.getPrice()) {
    //  this.user.asSender(Plugin.INFO).sendNotEnoughCoinsMessage((type.getPrice() - this.user.getCoins()));
    //  return false;
    //}
//
    //this.user.removeCoins(type.getPrice(), true);
    //this.shopManager.getPetManager().addPetForUser(this.user, type);
    //return true;
  }

  private Collection<Pet<?>> getUserPets() {
    return this.shopManager.getPetManager().getPetsByUser().get(this.user);
  }
}
