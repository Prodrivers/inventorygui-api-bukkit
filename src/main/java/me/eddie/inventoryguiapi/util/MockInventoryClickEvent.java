package me.eddie.inventoryguiapi.util;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class MockInventoryClickEvent extends InventoryClickEvent {
	public MockInventoryClickEvent(int slot) {
		super(
				new MockInventoryView(),
				InventoryType.SlotType.CONTAINER,
				slot,
				ClickType.LEFT,
				InventoryAction.PLACE_ONE
		);
	}
}
