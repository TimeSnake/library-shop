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
import de.timesnake.basic.bukkit.util.chat.Plugin;
import de.timesnake.basic.bukkit.util.user.ExItemStack;
import de.timesnake.basic.bukkit.util.user.event.UserInventoryInteractEvent;
import de.timesnake.basic.bukkit.util.user.event.UserInventoryInteractListener;
import de.timesnake.library.basic.util.chat.ExTextColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopManager {

    public static ExItemStack SHOPSELECTION = new ExItemStack(3, Material.ENDER_CHEST, "§dShop")
            .setMoveable(false).setDropable(false).setLore("§fClick to open the shop");

    private final JavaPlugin plugin;
    private final CategorySelection cs;

    public ShopManager(JavaPlugin plugin) {
        this.plugin = plugin;
        Server.printText(Plugin.INFO, "Shop loaded", "Shop");
        cs = new CategorySelection();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

}
