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
import org.bukkit.MusicInstrument;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.MusicInstrumentMeta;

public class ActionItemShop extends ShopSection {

  private static final int FIREWORK_PRICE = 0;
  private static final int FIREWORK_AMOUNT = 5;

  private static final int HORN_PRICE = 0;

  public ActionItemShop(ShopManager shopManager, User user) {
    super(shopManager, new ExItemStack(Material.FIREWORK_ROCKET),
        new ExInventory(9, "§9Fun"), user);
    this.loadItems();
  }

  private void loadItems() {
    this.inventory.setItemStack(1, new ExItemStack(Material.FIREWORK_ROCKET)
        .setDisplayName("Firework")
        .setLore("§7Get " + FIREWORK_AMOUNT + " firework rockets", "",
            "§6" + FIREWORK_PRICE + " " + Server.getCoinsName())
        .onClick(e -> this.tryBuy(FIREWORK_PRICE, () -> user.setItem(this.shopManager.getActionSlot(),
            this.createFireworkRocket().asQuantity(FIREWORK_AMOUNT))), true));

    this.inventory.setItemStack(2, new ExItemStack(Material.GOAT_HORN)
        .setDisplayName("Horn")
        .setLore("§7Get a horn", "", "§6" + HORN_PRICE + " " + Server.getCoinsName())
        .onClick(e -> this.tryBuy(HORN_PRICE,
            () -> user.setItem(this.shopManager.getActionSlot(), this.createRandomGoatHorn())), true));
  }

  private boolean tryBuy(int price, Runnable action) {
    if (user.getCoins() < price) {
      this.user.sendPluginTDMessage(Plugin.INFO, "Too few coins");
      return false;
    }
    user.removeCoins(price, true);
    action.run();
    user.closeInventory();
    return true;
  }

  private ExItemStack createRandomGoatHorn() {
    MusicInstrument instrument = switch (Server.getRandom().nextInt(8)) {
      case 0 -> MusicInstrument.PONDER_GOAT_HORN;
      case 1 -> MusicInstrument.SING_GOAT_HORN;
      case 2 -> MusicInstrument.SEEK_GOAT_HORN;
      case 3 -> MusicInstrument.FEEL_GOAT_HORN;
      case 4 -> MusicInstrument.ADMIRE_GOAT_HORN;
      case 5 -> MusicInstrument.CALL_GOAT_HORN;
      case 6 -> MusicInstrument.YEARN_GOAT_HORN;
      case 7 -> MusicInstrument.DREAM_GOAT_HORN;
      default -> null;
    };

    return new ExItemStack(Material.GOAT_HORN)
        .editExMeta(MusicInstrumentMeta.class, meta -> meta.setInstrument(instrument));
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
