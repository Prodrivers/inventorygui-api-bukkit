package me.eddie.inventoryguiapi.gui.elements;

import me.eddie.inventoryguiapi.gui.events.GUIEvent;
import me.eddie.inventoryguiapi.gui.session.GUISession;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a GUIElement that displays text.
 *
 * In a Bedrock form, last encountered element's content will be used as header in a Bedrock form, others will be ignored.
 */
public class LabelItem extends AbstractGUIElement {

	private ItemStack display;

	/**
	 * Construct a new LabelItem with a desired slot
	 *
	 * @param slot    The slot that this LabelItem should be placed into
	 * @param display The display ItemStack
	 */
	public LabelItem(int slot, ItemStack display) {
		super(slot);
		if(display == null) {
			throw new IllegalArgumentException("Display item must not be null");
		}
		this.display = display;
	}

	/**
	 * Construct a new LabelItem with no desired slot
	 *
	 * @param display The display ItemStack
	 */
	public LabelItem(ItemStack display) {
		this(AbstractGUIElement.NO_DESIRED_SLOT, display);
	}


	public ItemStack getDisplayItem() {
		return this.display;
	}

	public void setDisplayItem(ItemStack display) {
		this.display = display;
	}

	@Override
	public void onEvent(GUIEvent event) {
	}

	@Override
	public ItemStack getDisplay(Player viewer, GUISession session) {
		return getDisplayItem();
	}

    @Override
    public FormImage getFormImage(Player viewer, GUISession session) {
        return null;
    }

    @Override
	public boolean canAutoInsertIntoSlot(Player Viewer, GUISession session) {
		return false;
	}
}
