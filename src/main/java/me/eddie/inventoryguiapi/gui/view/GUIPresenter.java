package me.eddie.inventoryguiapi.gui.view;

import me.eddie.inventoryguiapi.gui.guis.SharedInventoryGUI;
import me.eddie.inventoryguiapi.gui.session.GUISession;
import org.bukkit.entity.Player;

/**
 * Class that takes the generated contents of an InventoryGUI and shows it to a player.
 * To customize an inventory view beyond what this is capable of, simply implements this class
 * and make your GUI use your version of GUIPresenter.
 */
public interface GUIPresenter {
    /**
     * Will update the viewer's view of the given GUISession. This will not recalculate what should be displayed though,
     * instead use {@link me.eddie.inventoryguiapi.gui.guis.InventoryGUI#updateContentsAndView(Player)} if this is what you require.
     * This method will also only do it for the provided viewer, in the case of shared inventories use
     * {@link SharedInventoryGUI#updateContentsAndView()} to update the view for all viewers.
     * Use synchronized to make this thread-safe if necessary.
     * @param viewer The viewer of the GUI. Or if it's a GUI with multiple viewers, any viewer of the GUI.
     * @param session The GUISession that determines what should be displayed
     */
    void updateView(final Player viewer, GUISession session);
}
