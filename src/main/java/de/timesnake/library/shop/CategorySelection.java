/*
 * Copyright (C) 2022 timesnake
 */

package de.timesnake.library.shop;

import de.timesnake.basic.bukkit.util.Server;
import de.timesnake.basic.bukkit.util.user.ExInventory;
import de.timesnake.basic.bukkit.util.user.ExItemStack;
import de.timesnake.basic.bukkit.util.user.event.UserInventoryClickEvent;
import de.timesnake.basic.bukkit.util.user.event.UserInventoryClickListener;
import de.timesnake.basic.bukkit.util.user.event.UserInventoryInteractEvent;
import de.timesnake.basic.bukkit.util.user.event.UserInventoryInteractListener;
import de.timesnake.library.basic.util.chat.ExTextColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CategorySelection implements InventoryHolder, UserInventoryInteractListener, UserInventoryClickListener {

    private static int invSize = 6 * 9;
    private final ExInventory inventory;

    private final HashMap<ExItemStack, ShopCategory> shopCategoryByItemId = new HashMap<>();

    public CategorySelection(){
        this.inventory = Server.createExInventory(invSize, "Shop", this);

        for(ShopCategory sc : ShopCategory.values()){
            inventory.setItemStack(sc.getSlot(), sc.getItem());
            shopCategoryByItemId.put(sc.getItem(), sc);
        }

        Server.getInventoryEventManager().addClickListener(this, this);
        Server.getInventoryEventManager().addInteractListener(this, ShopManager.SHOPSELECTION);
    }


    @Override
    public Inventory getInventory() {
        return inventory.getInventory();
    }

    @Override
    public void onUserInventoryInteract(UserInventoryInteractEvent e) {
        e.getUser().openInventory(getInventory());
    }

    @Override
    public void onUserInventoryClick(UserInventoryClickEvent e) {
        ExItemStack clickedItem = e.getClickedItem();
        ShopCategory category = shopCategoryByItemId.get(clickedItem);

        // TODO: Save all created inventories in a hashmap indexed by the user to reduce load
        ItemShop shop = new ItemShop(e.getUser(), category);
        e.getUser().openInventory(shop.getInventory());
    }

    public enum ShopCategory{
        FIREWORK(0, new ExItemStack(Material.FIREWORK_ROCKET).setDisplayName("§cFirework"), "Firework", Set.of(
                new ExItemStack(Material.REDSTONE).setSlot(0),
                new ExItemStack(Material.LAPIS_LAZULI).setSlot(1)
        )),
        PETS(1, getPlayerHead("MHF_Fox").setDisplayName("§6Pets"), "Pets", Set.of(
                getPlayerHead("MHF_Fox").setSlot(0),
                getPlayerHead("MHF_Sheep").setSlot(1)
        ));

        private final int slot;
        private final ExItemStack item;
        private final String name;
        private final Collection<ExItemStack> offer;

        ShopCategory(int slot, ExItemStack item, String name, Collection<ExItemStack> offer){
            this.slot = slot;
            this.item = item;
            this.name = name;
            this.offer = offer;
        }

        public ExItemStack getItem() {
            return item;
        }

        public int getSlot() {
            return slot;
        }

        public Collection<ExItemStack> getOffer() {
            return offer;
        }

        public String getName() {
            return name;
        }

        private static ExItemStack getPlayerHead(String name){
            ExItemStack head = new ExItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwner(name);
            head.setItemMeta(meta);
            return head;
        }


    }

}
