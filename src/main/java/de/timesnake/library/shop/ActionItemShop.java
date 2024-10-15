/*
 * Copyright (C) 2023 timesnake
 */

package de.timesnake.library.shop;

import de.timesnake.basic.bukkit.util.Server;
import de.timesnake.basic.bukkit.util.chat.Plugin;
import de.timesnake.basic.bukkit.util.user.User;
import de.timesnake.basic.bukkit.util.user.inventory.ExInventory;
import de.timesnake.basic.bukkit.util.user.inventory.ExItemStack;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.meta.FireworkMeta;

public class ActionItemShop extends ShopSection {

  private static final int FIREWORK_PRICE = 0;
  private static final int FIREWORK_AMOUNT = 5;

  public ActionItemShop(ShopManager shopManager, User user) {
    super(shopManager, new ExItemStack(Material.FIREWORK_ROCKET),
        new ExInventory(9, "§9Fun"), user);
    this.loadItems();
  }

  private void loadItems() {
    this.inventory.setItemStack(1, new ExItemStack(Material.FIREWORK_ROCKET)
        .setDisplayName("Firework")
        .setLore("§7Get " + FIREWORK_AMOUNT + " Firework Rockets", "",
            "§6" + FIREWORK_PRICE + " " + Server.getCoinsName())
        .onClick(e -> {
          if (user.getCoins() < FIREWORK_PRICE) {
            this.user.sendPluginTDMessage(Plugin.INFO, "Too few coins");
            return;
          }
          user.removeCoins(FIREWORK_PRICE, true);
          e.getUser().setItem(this.shopManager.getActionSlot(),
              this.createFireworkRocket().asQuantity(FIREWORK_AMOUNT));
          e.getUser().closeInventory();
        }, true));

  }

  private ExItemStack createFireworkRocket() {
    ExItemStack firework = new ExItemStack(Material.FIREWORK_ROCKET);
    FireworkMeta fireworkMeta = (FireworkMeta) firework.getItemMeta();
    fireworkMeta.setPower(1);
    fireworkMeta.addEffect(this.createFireworkEffect());
    firework.setItemMeta(fireworkMeta);
    return firework;
  }

  private FireworkEffect createFireworkEffect() {
    return switch (Server.getRandom().nextInt(4)) {
      case 0 -> FireworkEffect.builder()
          .withTrail()
          .withColor(Color.YELLOW, Color.RED)
          .withFade(Color.ORANGE)
          .with(FireworkEffect.Type.BALL_LARGE)
          .build();
      case 1 -> FireworkEffect.builder()
          .withTrail()
          .withColor(Color.BLUE, Color.YELLOW)
          .withFade(Color.PURPLE)
          .with(FireworkEffect.Type.STAR)
          .build();
      case 2 -> FireworkEffect.builder()
          .withFlicker()
          .withColor(Color.GREEN, Color.OLIVE, Color.LIME)
          .with(FireworkEffect.Type.BURST)
          .build();
      case 3 -> FireworkEffect.builder()
          .withColor(Color.WHITE, Color.BLUE, Color.AQUA)
          .with(FireworkEffect.Type.BALL)
          .withFlicker()
          .build();
      default -> null;
    };
  }

}
