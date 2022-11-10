/*
 * library-shop.main
 * Copyright (C) 2022 timesnake
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

package de.timesnake.library.shop;

import de.timesnake.basic.bukkit.util.Server;
import de.timesnake.basic.bukkit.util.user.ExInventory;
import de.timesnake.basic.bukkit.util.user.ExItemStack;
import de.timesnake.basic.bukkit.util.user.User;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class ItemShop implements InventoryHolder {

    private final int invSize = 6 * 9;
    private User user;
    private ExInventory inventory;

    public ItemShop(User user, CategorySelection.ShopCategory shopCategory){
        this.user = user;

        inventory = Server.createExInventory(invSize, shopCategory.getName());

        for(ExItemStack item : shopCategory.getOffer()){
            inventory.setItemStack(item);
        }

    }


    @Override
    public Inventory getInventory() {
        return inventory.getInventory();
    }
}
