package me.eddie.inventoryguiapi.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class MockInventoryView extends InventoryView {
	private static Inventory INVENTORY = null;
	private static final InventoryType TYPE = InventoryType.CHEST;

	private static void init() {
		if(INVENTORY == null) {
			INVENTORY = Bukkit.createInventory(() -> null, TYPE.getDefaultSize());
		}
	}

	@Override
	public Inventory getTopInventory() {
		init();
		return INVENTORY;
	}

	@Override
	public Inventory getBottomInventory() {
		init();
		return INVENTORY;
	}

	@Override
	public HumanEntity getPlayer() {
		return null;
	}

	@Override
	public InventoryType getType() {
		return TYPE;
	}

	@Override
	public String getTitle() {
		return getType().getDefaultTitle();
	}
}
