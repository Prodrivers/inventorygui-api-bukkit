package me.eddie.inventoryguiapi.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public class MockInventoryView implements InventoryView {
	private static Inventory INVENTORY = null;
	private static final InventoryType TYPE = InventoryType.CHEST;

	private static void init() {
		if(INVENTORY == null) {
			INVENTORY = Bukkit.createInventory(() -> null, TYPE.getDefaultSize());
		}
	}

	@Override
	public @NonNull Inventory getTopInventory() {
		init();
		return INVENTORY;
	}

	@Override
	public @NonNull Inventory getBottomInventory() {
		init();
		return INVENTORY;
	}

	@Override
	public HumanEntity getPlayer() {
		return null;
	}

	@Override
	public @NonNull InventoryType getType() {
		return TYPE;
	}

	@Override
	public void setItem(int i, ItemStack itemStack) {
		INVENTORY.setItem(i, itemStack);
	}

	@Override
	public ItemStack getItem(int i) {
		return INVENTORY.getItem(i);
	}

	@Override
	public void setCursor(ItemStack itemStack) {}

	@Override
	public ItemStack getCursor() {
		return null;
	}

	@Override
	public Inventory getInventory(int i) {
		return INVENTORY;
	}

	@Override
	public int convertSlot(int i) {
		return 0;
	}

	@Override
	public InventoryType.@NonNull SlotType getSlotType(int i) {
		return InventoryType.SlotType.CONTAINER;
	}

	@Override
	public void close() {}

	@Override
	public int countSlots() {
		return 0;
	}

	@Override
	public boolean setProperty(@SuppressWarnings("removal") @NonNull Property property, int i) {
		return false;
	}

	@Override
	public @NonNull String getTitle() {
		return getType().getDefaultTitle();
	}

	@Override
	public @NonNull String getOriginalTitle() {
		return getType().getDefaultTitle();
	}

	@Override
	public void setTitle(@NonNull String s) {}
}
